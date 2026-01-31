package org.example.onlinebankmanagementbackend.security.service;

import org.example.onlinebankmanagementbackend.security.dto.LoginRequestDTO;
import org.example.onlinebankmanagementbackend.security.dto.LoginResponseDTO;
import org.example.onlinebankmanagementbackend.security.dto.RegisterRequestDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    void register(RegisterRequestDTO registerRequestDTO);
}
