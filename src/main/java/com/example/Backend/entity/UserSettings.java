package com.example.Backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_settings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Builder.Default
    private boolean emailOnBooking       = true;   
    @Builder.Default
    private boolean emailOnReview        = true;   
    @Builder.Default
    private boolean appNotifications     = true;   

    
    @Builder.Default
    @Column(length = 10)
    private String theme = "light";             

    
    @Builder.Default
    @Column(length = 10)
    private String language = "en";               

    @Builder.Default
    @Column(length = 10)
    private String currency = "INR";
}