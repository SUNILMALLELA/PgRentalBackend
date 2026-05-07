package com.example.Backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.UpdateUserProfileDTO;
import com.example.Backend.dto.UserProfileResponseDTO;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserRepository userRepository;
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new CustomException("Authenticated user not found", 404));
    }

    public UserProfileResponseDTO getProfile() {
        User user = getCurrentUser();
        return new UserProfileResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    public UserProfileResponseDTO updateProfile(
        UpdateUserProfileDTO updateUserProfileDTO) {

    User user = getCurrentUser();
    user.setFullName(updateUserProfileDTO.getName());
    user.setPhoneNumber(updateUserProfileDTO.getPhoneNumber());
    User updatedUser = userRepository.save(user);
    return new UserProfileResponseDTO(
            updatedUser.getId(),
            updatedUser.getFullName(),
            updatedUser.getEmail(),
            updatedUser.getPhoneNumber()
    );
}
}
