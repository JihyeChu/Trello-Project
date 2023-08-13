package com.sparta.trelloproject.user.repository;

import com.sparta.trelloproject.user.entity.Password;
import com.sparta.trelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
    Password findByUser(User user);
}
