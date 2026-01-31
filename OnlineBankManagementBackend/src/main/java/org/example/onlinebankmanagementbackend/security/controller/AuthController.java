package org.example.onlinebankmanagementbackend.security.controller;

import jakarta.validation.Valid;
import org.example.onlinebankmanagementbackend.security.dto.LoginRequestDTO;
import org.example.onlinebankmanagementbackend.security.dto.LoginResponseDTO;
import org.example.onlinebankmanagementbackend.security.dto.RegisterRequestDTO;
import org.example.onlinebankmanagementbackend.security.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthService authService;

    public AuthController(AuthService authService)
    {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO loginRequestDTO)
    {
        LoginResponseDTO loginResponseDTO = authService.login(loginRequestDTO);
        return ResponseEntity.ok(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequestDTO request)
    {
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

}
