package com.example.short_link.module.OriginalLink.controller;


import com.example.short_link.module.OriginalLink.dto.request.ReqLinkOriginal;
import com.example.short_link.module.OriginalLink.dto.response.ResOriginalLink;
import com.example.short_link.module.OriginalLink.service.OriginalLinkService;
import com.example.short_link.shared.annotation.SendMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class OriginalLinkController {
//    private final OriginalLinkServiceImp originalLinkServiceImp;


    private final OriginalLinkService originalLinkService;

    public OriginalLinkController(OriginalLinkService originalLinkService) {
        this.originalLinkService = originalLinkService;
    }

    @PostMapping("")
    public ResponseEntity<ResOriginalLink> handleCreateShortLink(@Valid @RequestBody ReqLinkOriginal reqLinkOriginal) {
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(this.originalLinkService.createShortLink(reqLinkOriginal, null));
    }


    @GetMapping("")
    @SendMessage("Get All Link By Email")
    public ResponseEntity<List<ResOriginalLink>> handleGetALlOriginLink() {
        return ResponseEntity.ok().body(this.originalLinkService.getAllOriginalLinkByEmail());
    }


    @GetMapping("/admin")
    @SendMessage("Admin Get All Link")
    public ResponseEntity<List<ResOriginalLink>> handleGetALlOriginLinkByEmail() {
        return ResponseEntity.ok().body(this.originalLinkService.getAllOriginalLink());
    }


    @DeleteMapping("/{slug}")
    @SendMessage("Delete Original")
    public ResponseEntity<ResOriginalLink> handleDeleteOriginalLink(@PathVariable("slug") String slug) {
        return ResponseEntity.ok().body(this.originalLinkService.deleteOriginalLink(null, slug));
    }
}
