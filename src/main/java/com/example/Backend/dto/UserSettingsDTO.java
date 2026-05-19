package com.example.Backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSettingsDTO {

    private boolean emailOnBooking;
    private boolean emailOnReview;
    private boolean appNotifications;
    private String  theme;
    private String  language;
    private String  currency;
}
