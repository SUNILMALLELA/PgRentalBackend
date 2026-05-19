package com.example.Backend.service;

import com.example.Backend.dto.DashboardStatsDTO;
import com.example.Backend.dto.MonthlyBookingDTO;
import com.example.Backend.dto.UserBookingDTO;
import com.example.Backend.entity.BookingStatus;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.BookingRepository;
import com.example.Backend.repository.SavedPgRepository;
import com.example.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDashboardService {
    private final BookingRepository  bookingRepository;
    private final UserRepository     userRepository;
    private final SavedPgRepository  savedPgRepository;
    private static final String[] MONTH_NAMES = {
        "", "Jan","Feb","Mar","Apr","May","Jun",
        "Jul","Aug","Sep","Oct","Nov","Dec"
    };

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", 404));
    }

    public DashboardStatsDTO getStats() {
        User user   = getCurrentUser();
        Long userId = user.getId();
        LocalDateTime from = LocalDateTime.now()
                .minusMonths(11)
                .withDayOfMonth(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<MonthlyBookingDTO> monthly = bookingRepository
                .findMonthlyBookingsRaw(userId, from)
                .stream()
                .map(row -> {
                    int  year  = ((Number) row[0]).intValue();
                    int  month = ((Number) row[1]).intValue();
                    long count = ((Number) row[2]).longValue();
                    String monthName = (month >= 1 && month <= 12)
                            ? MONTH_NAMES[month] : "?";
                    return new MonthlyBookingDTO(monthName, year, count);
                })
                .toList();

        return DashboardStatsDTO.builder()
                .totalBookings(bookingRepository.countByUserId(userId))
                .approved(bookingRepository.countByUserIdAndStatus(userId, BookingStatus.APPROVED))
                .rejected(bookingRepository.countByUserIdAndStatus(userId, BookingStatus.REJECTED))
                .pending(bookingRepository.countByUserIdAndStatus(userId, BookingStatus.PENDING))
                .savedPgs(savedPgRepository.countByUser(user))
                .monthlyBookings(monthly)
                .build();
    }

    public List<UserBookingDTO> getAllMyBookings() {
        User user = getCurrentUser();
        return bookingRepository.findAllBookingsByUserId(user.getId());
    }
}