package com.example.Backend.service;

import com.example.Backend.dto.BookingRequestDTO;
import com.example.Backend.dto.BookingResponseDTO;
import com.example.Backend.entity.*;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.BookingRepository;
import com.example.Backend.repository.PgRepository;
import com.example.Backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PgRepository pgRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Authenticated user not found", 404));
    }

    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        User user = getCurrentUser();
        Pg pg = pgRepository.findById(dto.getPgId())
                .orElseThrow(() -> new CustomException("PG not found", 404));

        Booking booking = Booking.builder()
                .user(user)
                .pg(pg)
                .visitDate(dto.getVisitDate())
                .note(dto.getNote())
                .build();

        return mapToDTO(bookingRepository.save(booking));
    }

    public List<BookingResponseDTO> getMyBookings() {
        User user = getCurrentUser();
        return bookingRepository.findByUser(user)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<BookingResponseDTO> getOwnerBookings() {
        User owner = getCurrentUser();
        return bookingRepository.findByPg_Owner(owner)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public BookingResponseDTO updateBookingStatus(Long bookingId, BookingStatus newStatus) {
        User owner = getCurrentUser();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new CustomException("Booking not found", 404));

        if (!booking.getPg().getOwner().getId().equals(owner.getId())) {
            throw new CustomException("You are not the owner of this PG", 403);
        }

        booking.setStatus(newStatus);
        return mapToDTO(bookingRepository.save(booking));
    }

    private BookingResponseDTO mapToDTO(Booking booking) {
        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .pgTitle(booking.getPg().getTitle())
                .location(booking.getPg().getLocation())
                .price(booking.getPg().getPrice())
                .ownerName(booking.getPg().getOwner().getFullName())
                .ownerPhone(booking.getPg().getOwner().getPhoneNumber())
                .status(booking.getStatus().name())
                .bookedAt(booking.getCreatedAt() != null ? booking.getCreatedAt().toString() : null)
                .visitDate(booking.getVisitDate())
                .note(booking.getNote())
                .build();
    }
}
