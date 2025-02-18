package com.example.short_link.module.OriginalLink.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.OriginalLink.dto.request.ReqLinkOriginal;
import com.example.short_link.module.OriginalLink.dto.response.ResOriginalLink;
import com.example.short_link.module.OriginalLink.service.OriginalLinkService;
import com.example.short_link.shared.annotation.SendMessage;
import com.example.short_link.shared.error.NotFoundException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/link/quick")
public class OriginalLinkQuickController {


    @Value("${cookie.domain}")
    private String domain;


    private final OriginalLinkService originalLinkService;

    public OriginalLinkQuickController(OriginalLinkService originalLinkService) {
        this.originalLinkService = originalLinkService;
    }


    @PostMapping("")
    public ResponseEntity<ResOriginalLink> handleCreateShortLinkQuick(@Valid @RequestBody ReqLinkOriginal reqLinkOriginal, @CookieValue(name = "ShortLinkCookie", defaultValue = "default") String myCookie) {
        String UUID = null;
        if (myCookie.equals("default")) {
            UUID = this.originalLinkService.getRandomString(10);
        } else {
            UUID = myCookie;
        }

        // Tạo cookie
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from("ShortLinkCookie", UUID)
                .httpOnly(false)
                .path("/")
                .maxAge(86400)
                .domain(domain); // Chỉ dùng domain "localhost" khi làm việc trên localhost

        // Không sử dụng secure() vì bạn chạy trên HTTP
        ResponseCookie resCookie = cookieBuilder.build();


        return ResponseEntity.status(HttpStatus.CREATED.value())
                .header(HttpHeaders.SET_COOKIE, resCookie.toString())
                .body(this.originalLinkService.createShortLink(reqLinkOriginal, UUID));
    }


    @GetMapping("")
    @SendMessage("Get All Link")
    public ResponseEntity<List<ResOriginalLink>> handleGetAllShortlinkByUUDI(@CookieValue(name = "ShortLinkCookie", defaultValue = "default") String myCookie) {

        if (myCookie.equals("default")) {
            throw new NotFoundException("Not have link");
        }
        List<OriginalLink> listCollection = this.originalLinkService.getAllOriginalLinkByUUID(myCookie);
        List<ResOriginalLink> resOriginalLink = listCollection.stream().map(item -> new ResOriginalLink(item.getOriginalLink(),
                domain + "/" + item.getShortLink().getUrlShort(), item.getShortLink().getExpireAt())

        ).toList();
        return ResponseEntity.ok().body(resOriginalLink);
    }

    @DeleteMapping("/{slug}")
    @SendMessage("Delete Link")
    public ResponseEntity<Object> handleDeleteLink(@PathVariable String slug, @CookieValue(name = "ShortLinkCookie", defaultValue = "default") String myCookie) {

        if (myCookie.equals("default")) {
            throw new NotFoundException("Missing Cookie !");
        }

        return ResponseEntity.ok().body(this.originalLinkService.deleteOriginalLink(myCookie, slug));
    }

}
