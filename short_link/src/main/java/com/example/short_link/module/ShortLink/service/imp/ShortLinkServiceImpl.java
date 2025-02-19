package com.example.short_link.module.ShortLink.service.imp;

import com.example.short_link.module.OriginalLink.service.RedisService;
import com.example.short_link.module.ShortLink.domain.ShortLink;
import com.example.short_link.module.ShortLink.dto.response.ResShortLinkDTO;
import com.example.short_link.module.ShortLink.repository.ShortLinkRepository;
import com.example.short_link.module.ShortLink.service.ShortLinkService;
import com.example.short_link.shared.error.ExistException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    private final RedisService redisService;
    private final ShortLinkRepository shortLinkRepository;


    private final String keyShortLink = "ShortLink=";
    private final String countAccessShortLink = "CountAccessShortLink=";

    public ShortLinkServiceImpl(ShortLinkRepository shortLinkRepository, RedisService redisService) {
        this.shortLinkRepository = shortLinkRepository;
        this.redisService = redisService;
    }

    @Override
    public ShortLink createShortLink(ShortLink shortLink) {
        return this.shortLinkRepository.save(shortLink);
    }


    @Override
    public ResShortLinkDTO findShortLink(String link) {

        // key save Original link
        String keyRedisShortLink = keyShortLink + link;
        // key save count access
        String countKeyRedisShortLink = countAccessShortLink + link;

        String urlLinkRedis = this.redisService.getLinkOriginalbyShortLink(keyRedisShortLink);

        if (urlLinkRedis != null) {
            this.redisService.updateCountAccessShortLink(countKeyRedisShortLink);
            return new ResShortLinkDTO(urlLinkRedis);
        }

        Optional<ShortLink> shortLinkOptional = this.shortLinkRepository.findByUrlShort(link);

        if (!shortLinkOptional.isPresent()) {
            throw new ExistException("Not Found Short Link ");
        }

        ShortLink shortLink = shortLinkOptional.get();

        // tăng số lần cout_access trong db
//        this.incrementCount(shortLinkOptional.get());

        this.redisService.saveKeyString(keyRedisShortLink, shortLink.getOriginalLink().getOriginalLink());
        this.redisService.saveKey(countKeyRedisShortLink, shortLink.getCountAccess());


        return new ResShortLinkDTO(shortLink.getOriginalLink().getOriginalLink());


    }

    public void incrementCount(ShortLink shortLink) {
        shortLink.setCountAccess(shortLink.getCountAccess() + 1);
        this.shortLinkRepository.save(shortLink);
    }


    public boolean checkExistShortLink(String shortLink) {
        return this.shortLinkRepository.findByUrlShort(shortLink).isPresent() ? true : false;
    }


    public void deleteShortLink(ShortLink shortLink) {
        this.shortLinkRepository.delete(shortLink);

    }

}
