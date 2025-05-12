
package com.organization.taskdirectory;
import java.util.Optional;


import com.organization.taskdirectory.entity.Role;
import com.organization.taskdirectory.entity.User;
import com.organization.taskdirectory.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TaskdirectoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskdirectoryApplication.class, args);
    }

   @Bean
CommandLineRunner runner(UserRepository userRepository, PasswordEncoder encoder) {
    return args -> {
        // Add Admin
        userRepository.findByUsername("Admin")
                .or(() -> Optional.of(userRepository.save(User.builder()
                        .username("Admin")
                        .password(encoder.encode("admin1234"))
                        .role(Role.ADMINISTRATOR)
                        .build())));

        // Add Supervisor
        userRepository.findByUsername("Supervisor")
                .or(() -> Optional.of(userRepository.save(User.builder()
                        .username("Supervisor")
                        .password(encoder.encode("supervisor1234"))
                        .role(Role.SUPERVISOR)
                        .build())));

        // Add Users
        for (int i = 1; i <= 4; i++) {
            String username = "User" + i;
            userRepository.findByUsername(username)
                    .or(() -> Optional.of(userRepository.save(User.builder()
                            .username(username)
                            .password(encoder.encode("user1234"))
                            .role(Role.USER)
                            .build())));
        }
    };
}

}