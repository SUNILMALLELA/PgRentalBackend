package com.example.Backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.UpdateUserProfileDTO;
import com.example.Backend.dto.UserProfileResponseDTO;
import com.example.Backend.service.UserProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    @GetMapping("/profile")
    public UserProfileResponseDTO getProfile(){
        return userProfileService.getProfile();
    }

    @PutMapping("/update")
    public UserProfileResponseDTO updateProfile(@RequestBody UpdateUserProfileDTO updateUserProfileDTO){
        return userProfileService.updateProfile(updateUserProfileDTO);
    }

}
