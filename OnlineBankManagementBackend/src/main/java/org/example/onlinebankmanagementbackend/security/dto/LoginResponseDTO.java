package org.example.onlinebankmanagementbackend.security.dto;

import lombok.Getter;

@Getter
public class LoginResponseDTO
{
    private final String token;
    private final String userId;
    private final String role;

    public LoginResponseDTO(String token, String userId, String role)
    {
        this.token = token;
        this.userId = userId;
        this.role = role;
    }

}
