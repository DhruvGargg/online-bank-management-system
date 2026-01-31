package org.example.onlinebankmanagementbackend.exception;

public class InsufficientBalanceException extends RuntimeException
{
    public InsufficientBalanceException(String message)
    {
        super(message);
    }
}
