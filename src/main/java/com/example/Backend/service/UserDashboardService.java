package com.example.Backend.service;

import com.example.Backend.dto.DashboardStatsDTO;
import com.example.Backend.dto.UserBookingDTO;
import com.example.Backend.entity.BookingStatus;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.BookingRepository;
import com.example.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDashboardService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", 404));
    }

    public DashboardStatsDTO getStats() {
        User user = getCurrentUser();
        Long userId = user.getId();

        return DashboardStatsDTO.builder()
                .totalBookings(bookingRepository.countByUserId(userId))
                .approved(bookingRepository.countByUserIdAndStatus(userId, BookingStatus.APPROVED))
                .rejected(bookingRepository.countByUserIdAndStatus(userId, BookingStatus.REJECTED))
                .pending(bookingRepository.countByUserIdAndStatus(userId, BookingStatus.PENDING))
                .build();
    }

    public List<UserBookingDTO> getAllMyBookings() {
        User user = getCurrentUser();
        return bookingRepository.findAllBookingsByUserId(user.getId());
    }
}
