package de.sappich.springsecuritydemo.user;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String password;
}
