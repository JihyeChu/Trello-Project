package com.sparta.trelloproject.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
//    private Password passwordEntity;

    public void setEmail(String email) {
        this.email = email;
    }
    public User(String userName, String password, String email, UserRoleEnum role) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}