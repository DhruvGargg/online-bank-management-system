package org.example.onlinebankmanagementbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class OnlineBankManagementBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineBankManagementBackendApplication.class, args);
    }

}
