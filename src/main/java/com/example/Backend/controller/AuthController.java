package com.example.Backend.controller;

import com.example.Backend.dto.LoginRequestDTO;
import com.example.Backend.dto.LoginResponseDTO;
import com.example.Backend.dto.UserRequestDTO;
import com.example.Backend.dto.UserResponseDTO;
import com.example.Backend.entity.User;
import com.example.Backend.exception.CustomException;
import com.example.Backend.repository.UserRepository;
import com.example.Backend.security.JWTUtil;
import com.example.Backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO response = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CustomException("User not found", 404));

        String token = jwtUtil.generateToken(user.getEmail(), user.getFullName(), user.getRole().name());

        LoginResponseDTO response = LoginResponseDTO.builder()
                .token(token)
                .message("Login successful")
                .role(user.getRole().name())
                .fullName(user.getFullName())
                .build();

        return ResponseEntity.ok(response);
    }
}
