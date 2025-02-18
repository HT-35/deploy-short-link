package com.example.short_link.module.OriginalLink.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReqLinkOriginal {

    @NotBlank(message = "Missing Day")
    private String link;

    @NotNull
    @Min(1)
    private int numberCharacer;
}
