package com.sparta.trelloproject.user.repository;

import com.sparta.trelloproject.user.entity.Password;
import com.sparta.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    Password findByUser(User user);
}
