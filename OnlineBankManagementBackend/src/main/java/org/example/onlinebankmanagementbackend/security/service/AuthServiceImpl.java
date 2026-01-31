package org.example.onlinebankmanagementbackend.security.service;

import org.example.onlinebankmanagementbackend.entity.User;
import org.example.onlinebankmanagementbackend.enums.UserRole;
import org.example.onlinebankmanagementbackend.repository.UserRepository;
import org.example.onlinebankmanagementbackend.security.dto.LoginRequestDTO;
import org.example.onlinebankmanagementbackend.security.dto.LoginResponseDTO;
import org.example.onlinebankmanagementbackend.security.dto.RegisterRequestDTO;
import org.example.onlinebankmanagementbackend.security.jwt.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO)
    {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if(!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
        {
            throw new RuntimeException("Invalid credentials");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUserRole().name());
        String token = jwtService.generateToken(loginRequestDTO.getEmail());
        return new LoginResponseDTO(token,
                user.getUserId().toString(),
                user.getUserRole().name());
    }

    @Override
    public void register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new RuntimeException("Mobile number already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(UserRole.USER);

        userRepository.save(user);
    }

}
