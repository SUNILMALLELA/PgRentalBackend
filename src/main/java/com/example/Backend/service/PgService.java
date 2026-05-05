package com.example.Backend.service;

import com.example.Backend.dto.PgRequestDTO;
import com.example.Backend.dto.PgResponseDTO;
import com.example.Backend.entity.GenderPreference;
import com.example.Backend.entity.Pg;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.PgRepository;
import com.example.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PgService {

    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", 404));
    }

    public PgResponseDTO createPg(PgRequestDTO dto) {
        User owner = getCurrentUser();
        Pg pg = Pg.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .price(dto.getPrice())
                .genderPreference(GenderPreference.valueOf(dto.getGenderPreference().toUpperCase()))
                .owner(owner)
                .build();
        return mapToDTO(pgRepository.save(pg));
    }

    public List<PgResponseDTO> getAllPgs() {
        return pgRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PgResponseDTO getPgById(Long id) {
        Pg pg = pgRepository.findById(id)
                .orElseThrow(() -> new CustomException("PG not found", 404));
        return mapToDTO(pg);
    }

    public List<PgResponseDTO> getMyPgs() {
        User owner = getCurrentUser();
        return pgRepository.findByOwner(owner)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public PgResponseDTO updatePg(Long id, PgRequestDTO dto) {
        User owner = getCurrentUser();
        Pg pg = pgRepository.findById(id)
                .orElseThrow(() -> new CustomException("PG not found", 404));

        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new CustomException("You are not the owner of this PG", 403);
        }

        pg.setTitle(dto.getTitle());
        pg.setDescription(dto.getDescription());
        pg.setLocation(dto.getLocation());
        pg.setPrice(dto.getPrice());
        pg.setGenderPreference(GenderPreference.valueOf(dto.getGenderPreference().toUpperCase()));

        return mapToDTO(pgRepository.save(pg));
    }

    public String deletePg(Long id) {
        User owner = getCurrentUser();
        Pg pg = pgRepository.findById(id)
                .orElseThrow(() -> new CustomException("PG not found", 404));
        if (!pg.getOwner().getId().equals(owner.getId())) {
            throw new CustomException("You are not the owner of this PG", 403);
        }

        pgRepository.delete(pg);
        return "PG deleted successfully";
    }

    public List<PgResponseDTO> searchPgs(String location, Double minPrice,
                                          Double maxPrice, String gender) {
        GenderPreference genderEnum = null;
        if (gender != null && !gender.isBlank()) {
            genderEnum = GenderPreference.valueOf(gender.toUpperCase());
        }
        return pgRepository.searchPgs(location, minPrice, maxPrice, genderEnum)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private PgResponseDTO mapToDTO(Pg pg) {
        return PgResponseDTO.builder()
                .id(pg.getId())
                .title(pg.getTitle())
                .description(pg.getDescription())
                .location(pg.getLocation())
                .price(pg.getPrice())
                .genderPreference(pg.getGenderPreference().name())
                .ownerName(pg.getOwner().getFullName())
                .ownerPhone(pg.getOwner().getPhoneNumber())
                .createdAt(pg.getCreatedAt() != null ? pg.getCreatedAt().toString() : null)
                .build();
    }
}
