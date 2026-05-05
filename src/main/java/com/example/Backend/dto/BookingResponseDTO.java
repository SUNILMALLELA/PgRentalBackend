package com.example.Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private String pgTitle;
    private String location;
    private Double price;
    private String ownerName;
    private String ownerPhone;
    private String status;
    private String bookedAt;
    private String visitDate;
    private String note;
}
