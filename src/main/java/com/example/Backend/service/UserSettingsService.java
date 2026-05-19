package com.example.Backend.service;

import com.example.Backend.dto.UserSettingsDTO;
import com.example.Backend.entity.User;
import com.example.Backend.entity.UserSettings;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.repository.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSettingsService {

    private final UserSettingsRepository settingsRepository;
    private final UserRepository         userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", 404));
    }

    public UserSettingsDTO getSettings() {
        User user = getCurrentUser();
        UserSettings settings = settingsRepository.findByUser(user)
                .orElseGet(() -> createDefaults(user));
        return mapToDTO(settings);
    }

    public UserSettingsDTO updateSettings(UserSettingsDTO dto) {
        User user = getCurrentUser();
        UserSettings settings = settingsRepository.findByUser(user)
                .orElseGet(() -> createDefaults(user));

        settings.setEmailOnBooking(dto.isEmailOnBooking());
        settings.setEmailOnReview(dto.isEmailOnReview());
        settings.setAppNotifications(dto.isAppNotifications());

        if (dto.getTheme() != null &&
                (dto.getTheme().equals("light") || dto.getTheme().equals("dark"))) {
            settings.setTheme(dto.getTheme());
        }

        if (dto.getLanguage() != null && !dto.getLanguage().isBlank()) {
            settings.setLanguage(dto.getLanguage());
        }

        if (dto.getCurrency() != null && !dto.getCurrency().isBlank()) {
            settings.setCurrency(dto.getCurrency());
        }

        return mapToDTO(settingsRepository.save(settings));
    }
    public String deleteAccount() {
        User user = getCurrentUser();
        userRepository.delete(user);
        return "Account deleted successfully";
    }
    private UserSettings createDefaults(User user) {
        UserSettings defaults = UserSettings.builder().user(user).build();
        return settingsRepository.save(defaults);
    }

    private UserSettingsDTO mapToDTO(UserSettings s) {
        return UserSettingsDTO.builder()
                .emailOnBooking(s.isEmailOnBooking())
                .emailOnReview(s.isEmailOnReview())
                .appNotifications(s.isAppNotifications())
                .theme(s.getTheme())
                .language(s.getLanguage())
                .currency(s.getCurrency())
                .build();
    }
}