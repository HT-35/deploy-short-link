package com.example.short_link.module.ShortLink.service;

import com.example.short_link.module.ShortLink.domain.ShortLink;
import com.example.short_link.module.ShortLink.dto.response.ResShortLinkDTO;

public interface ShortLinkService {

    boolean checkExistShortLink(String shortlink);

    ShortLink createShortLink(ShortLink shortLink);

    ResShortLinkDTO findShortLink(String link);
}
