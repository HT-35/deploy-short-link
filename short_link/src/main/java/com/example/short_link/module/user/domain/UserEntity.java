package com.example.short_link.module.user.domain;


import com.example.short_link.module.OriginalLink.domain.OriginalLink;
import com.example.short_link.module.ShortLink.domain.ShortLink;
import com.example.short_link.module.user.constant.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("users")
    private List<OriginalLink> originalLink;


    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("users")
    private List<ShortLink> shortLink;
}
