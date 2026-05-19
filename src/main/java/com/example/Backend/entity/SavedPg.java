package com.example.Backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "saved_pgs",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "pg_id"}))
public class SavedPg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id", nullable = false)
    private Pg pg;

    @Column(nullable = false, updatable = false)
    private LocalDateTime savedAt;

    @PrePersist
    protected void onCreate() {
        this.savedAt = LocalDateTime.now();
    }
}