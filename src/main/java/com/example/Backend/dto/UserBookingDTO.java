package com.example.Backend.dto;

import com.example.Backend.entity.BookingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBookingDTO {

    private Long bookingId;

    private String pgTitle;

    private String location;

    private Double price;

    private String ownerName;

    private String ownerPhone;

    private BookingStatus status;

    private LocalDateTime createdAt;

    private String visitDate;

    private String note;
}