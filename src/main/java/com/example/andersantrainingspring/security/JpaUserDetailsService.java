package com.example.andersantrainingspring.security;

import com.example.andersantrainingspring.domain.User;
import com.example.andersantrainingspring.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository repo;

    public JpaUserDetailsService(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities(
                        u.getRoles().stream()
                                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
