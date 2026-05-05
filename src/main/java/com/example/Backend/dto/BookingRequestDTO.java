package com.example.Backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {

    @NotNull(message = "PG ID is required")
    private Long pgId;

    private String visitDate;

    private String note;
}
