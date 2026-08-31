package com.example.bglogger.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bglogger.dto.UserRegistrationDTO;
import com.example.bglogger.exceptions.UserAlreadyExistsException;
import com.example.bglogger.models.User;
import com.example.bglogger.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(UserRegistrationDTO dto) {
        String lowerCaseEmail = dto.getEmail().toLowerCase();

        if (userRepository.findByEmail(lowerCaseEmail).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + lowerCaseEmail);
        }

        User user = new User();
        user.setEmail(lowerCaseEmail);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());

        return userRepository.save(user);
    }
    
}
