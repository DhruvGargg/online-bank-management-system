package org.example.onlinebankmanagementbackend.notification.util;

import java.math.BigDecimal;

public class EmailTemplateUtil {

    public static String creditEmail(
            String name,
            String accountNumber,
            BigDecimal amount,
            BigDecimal balance
    ) {
        return """
                Dear %s,

                ₹%.2f has been CREDITED to your account %s.

                Available Balance: ₹%.2f

                If this wasn't you, please contact support immediately.

                – Your Bank
                """.formatted(name, amount.doubleValue(), accountNumber, balance.doubleValue());
    }

    public static String debitEmail(
            String name,
            String accountNumber,
            BigDecimal amount,
            BigDecimal balance
    ) {
        return """
                Dear %s,

                ₹%.2f has been DEBITED from your account %s.

                Available Balance: ₹%.2f

                If this wasn't you, please contact support immediately.

                – Your Bank
                """.formatted(name, amount.doubleValue(), accountNumber, balance.doubleValue());
    }

    // Overloaded method for backward compatibility
    public static String debitEmail(
            String name,
            String accountNumber,
            BigDecimal balance
    ) {
        return debitEmail(name, accountNumber, new BigDecimal("0.00"), balance);
    }
}
