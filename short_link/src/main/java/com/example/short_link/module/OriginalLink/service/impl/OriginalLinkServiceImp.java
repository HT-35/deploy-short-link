package com.example.short_link.module.OriginalLink.service.impl;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.OriginalLink.dto.request.ReqLinkOriginal;
import com.example.short_link.module.OriginalLink.dto.response.ResOriginalLink;
import com.example.short_link.module.OriginalLink.repository.OriginalLinkRepository;
import com.example.short_link.module.OriginalLink.service.OriginalLinkService;
import com.example.short_link.module.OriginalLink.service.RedisService;
import com.example.short_link.module.ShortLink.domain.ShortLink;
import com.example.short_link.module.ShortLink.service.ShortLinkService;
import com.example.short_link.module.user.domain.UserEntity;
import com.example.short_link.module.user.service.UserService;
import com.example.short_link.shared.error.ExistException;
import com.example.short_link.shared.error.NotFoundException;
import com.example.short_link.shared.security.SecurityContexUtil;

@Service
public class OriginalLinkServiceImp implements OriginalLinkService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static String keyEmail = "email:";
    public static String keyUUID = "UUID:";
    private final OriginalLinkRepository originalLinkRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final SecurityContexUtil securityContexUtil;
    private final RedisService redisService;
    private final ShortLinkService shortLinkService;
    private final UserService userService;

    // private final  String domain = "http://localhost:3000";
    private final String domain = "http://huytranfullstack.id.vn";


    public OriginalLinkServiceImp(OriginalLinkRepository originalLinkRepository, ShortLinkService shortLinkService, SecurityContexUtil securityContexUtil, UserService userService, RedisService redisService) {
        this.originalLinkRepository = originalLinkRepository;
        this.securityContexUtil = securityContexUtil;
        this.userService = userService;
        this.shortLinkService = shortLinkService;
        this.redisService = redisService;
    }


    public String getEmail() {
        return SecurityContexUtil.getCurrentUserLogin().orElse("");
    }

    @Override

    public List<ResOriginalLink> getAllOriginalLink() {

        List<OriginalLink> listOriginal = this.originalLinkRepository.findAll();

        return listOriginal.stream().map(item -> new ResOriginalLink(item.getOriginalLink(), item.getShortLink().getUrlShort(), item.getShortLink().getExpireAt())).toList();

//        return listResOriginalLinkList;
    }

    public String getRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
        }

        return sb.toString();
    }

    /**
     * Kiểm tra và giới hạn số lượng link đã tạo theo UUID
     */
    private void validateShortLinkLimit(String UUID) {
        Integer linkCount = redisService.getKeyCount(UUID);



        if (linkCount == null) {
            int dbCount = this.getAllOriginalLinkByUUID(UUID).size();
            redisService.saveKey(keyUUID + UUID, dbCount);
            if (dbCount > 5) {
                throw new ExistException("Bạn đã tạo 3 short link");
            }
        } else if (linkCount > 5) {
            throw new ExistException("Bạn đã tạo 3 short link");
        }

    }

    /**
     * Create Unique ShortLink
     */
    private String generateUniqueShortLink(int length) {
        String randomString;
        int attempt = 0;
        do {
            if (attempt++ > 10) {
                throw new RuntimeException("Failed to gennerate unique short link after 10 attempts");
            }
            randomString = this.getRandomString(length);
        } while (shortLinkService.checkExistShortLink(randomString));
        return randomString;
    }

    /**
     * Check User and limit link create max 5 link
     */
    private UserEntity validateAndGetUser(String email) {

        System.out.println(email);

        // check count link of user by email
        Integer linkCount = this.redisService.getKeyCount(keyEmail + email);

        if (linkCount == null || linkCount < 5) {
            UserEntity user = this.userService.handleGetUserByUsername(email);
            if (user == null) {
                throw new ExistException("Not Found User");
            }
            int dbCount = originalLinkRepository.findByUsers(user).size();
            this.redisService.saveKey(keyEmail + email, dbCount);

            if (dbCount > 5) {
                throw new ExistException("You created 5 shortlink");
            }
            return user;
        }
        // created more than 5 link
        throw new ExistException("you create 5 short link");
    }

    /**
     * Define time expire
     */
    private Instant determineExpiry(String UUID) {
        return (UUID != null) ? Instant.now().plus(12, ChronoUnit.HOURS) : Instant.now().plus(7, ChronoUnit.DAYS);
    }

    /**
     * increase link in redis
     */
    private void incrementRedisCounter(String UUID, String email) {
        if (UUID != null) {
            this.redisService.incrementKey(keyUUID + UUID);
        } else {
            this.redisService.incrementKey(keyEmail + email);
        }
    }

    @Override
    public ResOriginalLink createShortLink(ReqLinkOriginal reqLinkOriginal, String UUID) {

        // check limit create short link free
        if (UUID != null) {
            this.validateShortLinkLimit(UUID);
        }


        String email = this.getEmail();


        // find Original Link by Link
        Optional<List<OriginalLink>> originalLinkOtp = this.originalLinkRepository
                .findByOriginalLink(reqLinkOriginal.getLink());

        boolean isPresentOriginalLink = originalLinkOtp.isPresent();

        /**
         * check case UUID  :   isPresentOriginalLink  &&  originalLink.getUUID == UUID
         * */
        boolean isSameUUID = UUID != null && isPresentOriginalLink && originalLinkOtp.map(list -> list.stream().anyMatch(item -> UUID.equals(item.getUUID()))).orElse(false);

        /**
         * check case Email :   isPresentOriginalLink  &&  originalLink.getEmail == Email
         * */
        boolean isSameUser = !email.isEmpty() && originalLinkOtp.isPresent() && originalLinkOtp.get().stream().anyMatch(item -> {
            String userEmail = item.getUsers() != null ? item.getUsers().getEmail() : "null";
            System.out.println("So sánh với email: " + userEmail);
            return item.getUsers() != null && email.equals(userEmail);
        });

        System.out.println("isSameUser: " + isSameUser);


        if (isSameUUID || isSameUser) {
            throw new ExistException("check Original Link exist");
        }


        String randomString = this.generateUniqueShortLink(reqLinkOriginal.getNumberCharacer());


        OriginalLink newOriginalLink = new OriginalLink();

        ShortLink newShortLink = new ShortLink();


        Integer getKeyCountByEmail = this.redisService.getKeyCount(keyEmail + email);


        UserEntity user = null;

        if (UUID == null) {
            user = validateAndGetUser(email);
        }


        newOriginalLink.setUUID(UUID);

        newOriginalLink.setUsers(user);

        newOriginalLink.setOriginalLink(reqLinkOriginal.getLink());

        OriginalLink saveOriginalLink = this.originalLinkRepository.save(newOriginalLink);


        // set time expireAt
        newShortLink.setExpireAt(this.determineExpiry(UUID));

        newShortLink.setUrlShort(randomString);
        newShortLink.setOriginalLink(saveOriginalLink);

        newShortLink.setUsers(user);

        this.shortLinkService.createShortLink(newShortLink);


        this.incrementRedisCounter(UUID, email);


        return new ResOriginalLink(reqLinkOriginal.getLink(), domain+"/" + randomString, newShortLink.getExpireAt());


    }

    @Override
    public List<OriginalLink> getAllOriginalLinkByUUID(String UUID) {


        return this.originalLinkRepository.findByUUID(UUID);

    }

    @Override
    public List<OriginalLink> getAllOriginalLinkByEmail(String email) {
        UserEntity user = this.userService.handleGetUserByUsername(email);
        return this.originalLinkRepository.findByUsers(user);
    }

    @Override
    public List<ResOriginalLink> getAllOriginalLinkByEmail() {
        UserEntity user = this.userService.handleGetUserByUsername(this.getEmail());
        List<OriginalLink> listOriginalLink = this.originalLinkRepository.findByUsers(user);
        return listOriginalLink.stream().map(item -> new ResOriginalLink(
                item.getOriginalLink(),
                domain+"/" + item.getShortLink().getUrlShort(),
                item.getShortLink().getExpireAt()
        )).toList();
    }


    @Override
    public ResOriginalLink deleteOriginalLink(String UUID, String Slug) {
        String email = null;
        List<OriginalLink> listOriginalLink = null;

        if (UUID == null) {
            email = this.getEmail();
            listOriginalLink = this.getAllOriginalLinkByEmail(email);
        } else {
            listOriginalLink = this.originalLinkRepository.findByUUID(UUID);
        }


        Long idLinks = listOriginalLink.stream().filter(item -> item.getShortLink() != null && item.getShortLink().getUrlShort().equals(Slug)).map(item -> item.getShortLink().getId()).findFirst().orElse(null);


        OriginalLink originalLink = listOriginalLink.stream().filter(item -> item.getShortLink() != null && idLinks != null && idLinks.equals(item.getShortLink().getId())).findFirst().orElse(null);


        if (idLinks == null || originalLink == null) {
            throw new NotFoundException("Not Found Link");
        }

        this.originalLinkRepository.deleteById(idLinks);

        this.redisService.removekey(keyEmail + email);

        return new ResOriginalLink(originalLink.getOriginalLink(), domain+"/" + originalLink.getShortLink().getUrlShort(), originalLink.getShortLink().getExpireAt());
    }


}