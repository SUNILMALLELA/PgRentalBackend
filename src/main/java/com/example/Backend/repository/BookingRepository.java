package com.example.Backend.repository;

import com.example.Backend.entity.Booking;
import com.example.Backend.entity.BookingStatus;
import com.example.Backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByPg_Owner(User owner);

    long countByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, BookingStatus status);
    @Query("""
        SELECT
            YEAR(b.createdAt),
            MONTH(b.createdAt),
            COUNT(b)
        FROM Booking b
        WHERE b.user.id = :userId
          AND b.createdAt >= :from
        GROUP BY
            YEAR(b.createdAt),
            MONTH(b.createdAt)
        ORDER BY
            YEAR(b.createdAt)  ASC,
            MONTH(b.createdAt) ASC
        """)
    List<Object[]> findMonthlyBookingsRaw(
        @Param("userId") Long userId,
        @Param("from")   LocalDateTime from
    );
    @Query("""
        SELECT new com.example.Backend.dto.UserBookingDTO(
            b.id,
            b.pg.title,
            b.pg.location,
            b.pg.price,
            b.pg.owner.fullName,
            b.pg.owner.phoneNumber,
            b.status,
            b.createdAt,
            b.visitDate,
            b.note
        )
        FROM Booking b
        WHERE b.user.id = :userId
        ORDER BY b.createdAt DESC
        """)
    List<com.example.Backend.dto.UserBookingDTO> findAllBookingsByUserId(
        @Param("userId") Long userId
    );
}
