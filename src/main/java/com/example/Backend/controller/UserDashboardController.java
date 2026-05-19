package com.example.Backend.controller;

import com.example.Backend.dto.DashboardStatsDTO;
import com.example.Backend.dto.UserBookingDTO;
import com.example.Backend.service.UserDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/dashboard")
@RequiredArgsConstructor
public class UserDashboardController {

    private final UserDashboardService userDashboardService;
    @GetMapping("/stats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        return ResponseEntity.ok(userDashboardService.getStats());
    }

    @GetMapping("/bookings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserBookingDTO>> getAllMyBookings() {
        return ResponseEntity.ok(userDashboardService.getAllMyBookings());
    }
}
