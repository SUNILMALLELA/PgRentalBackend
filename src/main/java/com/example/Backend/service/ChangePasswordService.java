package com.example.Backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Backend.dto.ChangePasswordDTO;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChangePasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(()-> new CustomException("User not found", 404));
    }

    public void changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = getCurrentUser();
        if(!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword())){
            throw new CustomException(
                    "Current password is incorrect",
                    400);
        }
        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())){
             throw new CustomException(
                    "Passwords do not match",
                    400);
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
    }
    
}
