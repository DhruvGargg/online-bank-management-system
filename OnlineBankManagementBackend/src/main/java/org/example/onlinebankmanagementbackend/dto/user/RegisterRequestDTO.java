package org.example.onlinebankmanagementbackend.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO
{
    private String username;
    private String password;
    private String mobileNumber;
    private String email;
    private String role;

}
