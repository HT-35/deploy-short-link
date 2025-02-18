package com.example.short_link.module.OriginalLink.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResOriginalLink {

    private String originalLink;
    private String shortLink;
    private Instant expireDate;
}
