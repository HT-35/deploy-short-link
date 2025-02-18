package com.example.short_link.module.ShortLink.domain;

import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.user.domain.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "ShortLink")
@Getter
@Setter
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Not Blank")
    @Column(unique = true, nullable = false)
    private String urlShort;

    private Instant expireAt;


    @Column(nullable = false, columnDefinition = "int default 0")
    private int countAccess;

    @OneToOne()
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private OriginalLink originalLink;

    @ManyToOne()
    @JoinColumn(name = "userId")
    @JsonIgnore
    private UserEntity users;


}
