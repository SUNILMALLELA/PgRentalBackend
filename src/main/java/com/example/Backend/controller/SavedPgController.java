package com.example.Backend.controller;

import com.example.Backend.dto.SavedPgResponseDTO;
import com.example.Backend.service.SavedPgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-pgs")
@RequiredArgsConstructor
public class SavedPgController {

    private final SavedPgService savedPgService;
    @PostMapping("/{pgId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SavedPgResponseDTO> savePg(@PathVariable Long pgId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPgService.savePg(pgId));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<SavedPgResponseDTO>> getMySavedPgs() {
        return ResponseEntity.ok(savedPgService.getMySavedPgs());
    }

    @DeleteMapping("/{pgId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> unsavePg(@PathVariable Long pgId) {
        return ResponseEntity.ok(savedPgService.unsavePg(pgId));
    }

    @GetMapping("/{pgId}/status")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Boolean> isSaved(@PathVariable Long pgId) {
        return ResponseEntity.ok(savedPgService.isSaved(pgId));
    }
}