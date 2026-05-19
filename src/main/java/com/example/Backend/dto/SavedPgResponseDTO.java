package com.example.Backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavedPgResponseDTO {

    private Long savedId;
    private Long pgId;
    private String title;
    private String description;
    private String location;
    private Double price;
    private String genderPreference;
    private String ownerName;
    private String ownerPhone;
    private String savedAt;
}
