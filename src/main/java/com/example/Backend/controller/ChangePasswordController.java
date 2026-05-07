package com.example.Backend.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Backend.dto.ChangePasswordDTO;
import com.example.Backend.service.ChangePasswordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class ChangePasswordController {
    private final ChangePasswordService changePasswordService;
    @PutMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordDTO changePasswordDTO ){
    changePasswordService.changePassword(changePasswordDTO);
        return "Password changed successfully";
    }
    
}
