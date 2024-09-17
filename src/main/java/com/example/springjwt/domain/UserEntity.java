package com.example.springjwt.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Email
    @NotNull
    private String email;

    private String role;

}
