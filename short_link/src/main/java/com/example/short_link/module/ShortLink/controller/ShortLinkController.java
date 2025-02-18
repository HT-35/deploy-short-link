package com.example.short_link.module.ShortLink.controller;


import com.example.short_link.module.ShortLink.dto.response.ResShortLinkDTO;
import com.example.short_link.module.ShortLink.service.ShortLinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shortlink")
public class ShortLinkController {


    private final ShortLinkService shortLinkService;

    public ShortLinkController(ShortLinkService shortLinkService) {
        this.shortLinkService = shortLinkService;
    }

    @GetMapping("/{link}")
    public ResponseEntity<ResShortLinkDTO> handleGetDetail(@PathVariable String link) {
        return ResponseEntity.ok().body(this.shortLinkService.findShortLink(link));
    }


}
