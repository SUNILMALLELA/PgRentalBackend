package com.example.Backend.controller;

import com.example.Backend.dto.UserSettingsDTO;
import com.example.Backend.service.UserSettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final UserSettingsService settingsService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public ResponseEntity<UserSettingsDTO> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public ResponseEntity<UserSettingsDTO> updateSettings(
            @RequestBody UserSettingsDTO dto) {
        return ResponseEntity.ok(settingsService.updateSettings(dto));
    }

    @DeleteMapping("/account")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public ResponseEntity<String> deleteAccount() {
        return ResponseEntity.ok(settingsService.deleteAccount());
    }
}

