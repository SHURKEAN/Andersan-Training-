package com.example.andersantrainingspring.config;

import com.example.andersantrainingspring.domain.Role;
import com.example.andersantrainingspring.domain.User;
import com.example.andersantrainingspring.repository.RoleRepository;
import com.example.andersantrainingspring.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initUsers(RoleRepository roles,
                                UserRepository users,
                                PasswordEncoder enc) {

        return args -> {
            if (roles.count() == 0) {
                Role adminRole = roles.save(new Role("ADMIN"));
                Role userRole  = roles.save(new Role("USER"));

                users.save(new User("admin",
                        enc.encode("admin123"),
                        Set.of(adminRole)));

                users.save(new User("user",
                        enc.encode("user123"),
                        Set.of(userRole)));
            }
        };
    }
}
