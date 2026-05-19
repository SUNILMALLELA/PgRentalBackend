package com.example.Backend.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardStatsDTO {

    private long totalBookings;
    private long approved;
    private long rejected;
    private long pending;
    private long savedPgs;
    private List<MonthlyBookingDTO> monthlyBookings;
}
