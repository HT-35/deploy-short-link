package com.example.short_link.module.OriginalLink.controller;


import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.OriginalLink.dto.request.ReqLinkOriginal;
import com.example.short_link.module.OriginalLink.dto.response.ResOriginalLink;
import com.example.short_link.module.OriginalLink.service.OriginalLinkService;
import com.example.short_link.shared.annotation.SendMessage;
import com.example.short_link.shared.error.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link/quick")
public class OriginalLinkQuickController {
//    private final OriginalLinkServiceImp originalLinkServiceImp;

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
                .domain("localhost"); // Chỉ dùng domain "localhost" khi làm việc trên localhost

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
        List<ResOriginalLink> resOriginalLink = listCollection.stream().map(item -> new ResOriginalLink(item.getOriginalLink(), "localhost:3000/"+item.getShortLink().getUrlShort(), item.getShortLink().getExpireAt())

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
