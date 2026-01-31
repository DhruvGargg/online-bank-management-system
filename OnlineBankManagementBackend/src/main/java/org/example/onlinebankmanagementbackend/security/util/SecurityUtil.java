package org.example.onlinebankmanagementbackend.security.util;

import org.example.onlinebankmanagementbackend.security.user.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId()
    {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof CustomUserDetails)
        {
            return getCurrentUserId();
        }

        throw new RuntimeException("Unauthorized");
    }
}
