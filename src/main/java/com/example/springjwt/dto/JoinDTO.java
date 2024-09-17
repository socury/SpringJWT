package com.example.springjwt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Email
    private String email;

}
