package com.example.Backend.controller;

import com.example.Backend.dto.PgRequestDTO;
import com.example.Backend.dto.PgResponseDTO;
import com.example.Backend.service.PgService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pgs")
@RequiredArgsConstructor
public class PgController {

    private final PgService pgService;
    @PostMapping
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PgResponseDTO> createPg(@Valid @RequestBody PgRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pgService.createPg(dto));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public ResponseEntity<List<PgResponseDTO>> getAllPgs() {
        return ResponseEntity.ok(pgService.getAllPgs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public ResponseEntity<PgResponseDTO> getPgById(@PathVariable Long id) {
        return ResponseEntity.ok(pgService.getPgById(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public ResponseEntity<List<PgResponseDTO>> searchPgs(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String gender) {
        return ResponseEntity.ok(pgService.searchPgs(location, minPrice, maxPrice, gender));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<PgResponseDTO>> getMyPgs() {
        return ResponseEntity.ok(pgService.getMyPgs());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<PgResponseDTO> updatePg(@PathVariable Long id,
                                                   @Valid @RequestBody PgRequestDTO dto) {
        return ResponseEntity.ok(pgService.updatePg(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<String> deletePg(@PathVariable Long id) {
        return ResponseEntity.ok(pgService.deletePg(id));
    }
}
