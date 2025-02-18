package com.example.short_link.module.user.constant;


import lombok.Getter;

@Getter
public enum RoleEnum {
    User("User"),
    Admin("Admin");


    private  final String description;

    private RoleEnum(String description){
        this.description = description;
    }

}
