package com.example.Backend.repository;

import com.example.Backend.entity.User;
import com.example.Backend.entity.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {
    Optional<UserSettings> findByUser(User user);
}
