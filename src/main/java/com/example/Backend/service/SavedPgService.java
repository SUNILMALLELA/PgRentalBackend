package com.example.Backend.service;

import com.example.Backend.dto.SavedPgResponseDTO;
import com.example.Backend.entity.Pg;
import com.example.Backend.entity.SavedPg;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.PgRepository;
import com.example.Backend.repository.SavedPgRepository;
import com.example.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SavedPgService {

    private final SavedPgRepository savedPgRepository;
    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", 404));
    }

    public SavedPgResponseDTO savePg(Long pgId) {
        User user = getCurrentUser();
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new CustomException("PG not found", 404));
        if (savedPgRepository.existsByUserAndPg(user, pg)) {
            throw new CustomException("PG already saved", 409);
        }

        SavedPg savedPg = SavedPg.builder()
                .user(user)
                .pg(pg)
                .build();

        return mapToDTO(savedPgRepository.save(savedPg));
    }

    public List<SavedPgResponseDTO> getMySavedPgs() {
        User user = getCurrentUser();
        return savedPgRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional
    public String unsavePg(Long pgId) {
        User user = getCurrentUser();
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new CustomException("PG not found", 404));

        if (!savedPgRepository.existsByUserAndPg(user, pg)) {
            throw new CustomException("PG not in saved list", 404);
        }

        savedPgRepository.deleteByUserAndPg(user, pg);
        return "PG removed from saved list";
    }

    public boolean isSaved(Long pgId) {
        User user = getCurrentUser();
        Pg pg = pgRepository.findById(pgId)
                .orElseThrow(() -> new CustomException("PG not found", 404));
        return savedPgRepository.existsByUserAndPg(user, pg);
    }

    private SavedPgResponseDTO mapToDTO(SavedPg savedPg) {
        Pg pg = savedPg.getPg();
        return SavedPgResponseDTO.builder()
                .savedId(savedPg.getId())
                .pgId(pg.getId())
                .title(pg.getTitle())
                .description(pg.getDescription())
                .location(pg.getLocation())
                .price(pg.getPrice())
                .genderPreference(pg.getGenderPreference().name())
                .ownerName(pg.getOwner().getFullName())
                .ownerPhone(pg.getOwner().getPhoneNumber())
                .savedAt(savedPg.getSavedAt() != null ? savedPg.getSavedAt().toString() : null)
                .build();
    }
}
