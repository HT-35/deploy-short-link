package com.example.short_link.module.OriginalLink.domain;

import com.example.short_link.module.ShortLink.domain.ShortLink;
import com.example.short_link.module.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "originalLink")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OriginalLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "enter your url")
    private String originalLink;

    private String UUID;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private UserEntity users;

    @OneToOne(mappedBy = "originalLink", cascade = CascadeType.ALL)
    private ShortLink shortLink;
}
