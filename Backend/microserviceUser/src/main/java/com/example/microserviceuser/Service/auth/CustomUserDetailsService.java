package com.example.microserviceuser.Service.auth;

import com.example.microserviceuser.Entity.User;
import com.example.microserviceuser.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        System.out.println("Utilisateur connecté : ID = " + user.getId());
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())


                .build();
    }
    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username);  // Récupérer l'utilisateur depuis la base de données
        if (user != null) {
            return user.getId();  // Retourner l'ID de l'utilisateur
        }
        return null;
    }
    public String getUserRoleByUsername(String username) {
        User user = userRepository.findByUsername(username);  // Récupérer l'utilisateur depuis la base de données
        if (user != null) {
            return user.getRole();  // Retourner l'ID de l'utilisateur
        }
        return null;
    }
}