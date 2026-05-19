package com.example.Backend.dto;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MonthlyBookingDTO {
    private String month;   
    private int    year;
    private long   count;   
}
