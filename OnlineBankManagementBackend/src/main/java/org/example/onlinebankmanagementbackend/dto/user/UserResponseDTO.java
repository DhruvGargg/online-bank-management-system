package org.example.onlinebankmanagementbackend.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO
{
    private String username;
    private String mobileNumber;
    private String email;
    private String id;
    private String accountNumber;
    private String createdAt;
    private String updatedAt;
    private String currency;
    private String balance;
    private String description;
    private String accountHolderName;
    private String transactionId;
    private String status;
    private String theme;
    private String type;
    private String currencySymbol;
    private String currencyCode;
    private String currencyName;

}
