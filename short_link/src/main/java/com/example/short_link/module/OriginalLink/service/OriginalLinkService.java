package com.example.short_link.module.OriginalLink.service;

import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.OriginalLink.dto.request.ReqLinkOriginal;
import com.example.short_link.module.OriginalLink.dto.response.ResOriginalLink;

import java.util.List;


public interface OriginalLinkService {

    String getRandomString(int length);

    List<OriginalLink> getAllOriginalLinkByUUID(String UUID);

    ResOriginalLink createShortLink(ReqLinkOriginal reqLinkOriginal, String UUID);

    List<ResOriginalLink> getAllOriginalLink();

    ResOriginalLink deleteOriginalLink(String UUID, String Slug);

    List<OriginalLink> getAllOriginalLinkByEmail(String email);

    List<ResOriginalLink> getAllOriginalLinkByEmail();

}