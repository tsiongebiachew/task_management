# Task Management System - Project

A Spring Boot application for managing task workflows with role-based access (Admin, Supervisor, User).

## Technologies
- Backend: Java (Spring Boot)
- Frontend: Thymeleaf
- Database: MySQL
- Security: Spring Security

##Default Users
| Username    | Password       | Role          |
|-------------|---------------|---------------|
| Admin       | admin1234     | Administrator |
| Supervisor  | supervisor1234| Supervisor    |
| User1-4     | user1234      | User          |

## Task Status Flow
1. Admin/Supervisor assigns task → Assigned
2. User updates → Completed
3. Supervisor verifies → Supervisor Reviewed
4. Admin approves → Admin Reviewed

## Key Features
- Role-based dashboard access
- Task assignment with document uploads
- Status tracking with approval workflow
- Responsive Thymeleaf UI

##  Setup
1. Database: Import schema.sql to MySQL
2. Configuration: Update application.properties
3. Run: 
`bash
mvn spring-boot:run
