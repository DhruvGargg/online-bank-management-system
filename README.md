🏦 OnlineBankManagementSystem

A secure and scalable Online Banking Management System built using Spring Boot, designed to handle user authentication, email-based communication, and automated banking notifications.
The system focuses on real-world backend architecture, security, and production-style features used in modern fintech applications.

✨ Features
🔐 Authentication System

User Registration with validation

Secure Login system

Role-based access control (User/Admin ready structure)

📧 Email Notification System (SMTP)

Automated email notifications using SMTP

Account-related alerts

Transaction & activity notifications

Bank statement sent via email

📄 Statement Management

User statements generated

Statements automatically sent to registered email

Email-based digital record keeping

🧑‍💼 Admin Controls (Advanced Feature)

Admin controller for account freeze/unfreeze

Admin control over statement email notifications

System-level user management

🛠️ Tech Stack
Layer	Technology
Backend	Spring Boot
Security	Spring Security
Authentication	JWT
Database	MySQL
ORM	JPA / Hibernate
Email Service	SMTP
Build Tool	Maven
Architecture	RESTful APIs
Server	Embedded Tomcat
🧩 Modules

Authentication Module (Login/Register)

User Management Module

Email Notification Module

Statement Processing Module

Admin Control Module

Security & Authorization Module

📂 Project Structure
OnlineBankManagementSystem
│
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── config
├── util
└── resources

🔄 System Workflow

User registers on the platform

Secure login authentication

User activities generate records

System sends:

Email notifications

Statement emails

Admin can:

Freeze/unfreeze accounts

Enable/disable statement notifications

⚙️ Setup Instructions
Prerequisites

Java 17+

Maven

MySQL

SMTP Email Account (Gmail / Outlook etc.)

Steps
git clone https://github.com/your-username/OnlineBankManagementSystem.git
cd OnlineBankManagementSystem


Configure application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/onlinebank
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true


Run project:

mvn spring-boot:run

🔐 Security Features

JWT-based authentication

Encrypted passwords

Role-based authorization

Protected APIs

Secure email handling

🎯 Learning Outcomes

Real-world Spring Boot architecture

SMTP integration

Secure authentication flows

Backend system design

Modular coding practices

Fintech system modeling

🚀 Future Enhancements

Transaction system

Payment gateway integration

OTP verification

Account analytics dashboard

Admin dashboard UI

Notification preferences

Mobile app integration

📌 Project Goal

To build a production-grade backend system simulating real banking workflows with:

Security

Automation

Email-based communication

Scalable architecture

Enterprise design patterns

👨‍💻 Developer

Dhruv Garg
B.Tech CSE | Backend Developer | Java | Spring Boot
LeetCode | DSA | System Design | Cloud Enthusiast
