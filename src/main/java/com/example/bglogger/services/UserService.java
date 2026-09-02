package com.example.bglogger.services;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bglogger.dto.UserProfileEditDTO;
import com.example.bglogger.dto.UserRegistrationDTO;
import com.example.bglogger.exceptions.InvalidTokenException;
import com.example.bglogger.exceptions.TokenExpiredException;
import com.example.bglogger.exceptions.UserAlreadyExistsException;
import com.example.bglogger.exceptions.UsernameTakenException;
import com.example.bglogger.models.EmailVerification;
import com.example.bglogger.models.User;
import com.example.bglogger.repositories.EmailVerificationRepository;
import com.example.bglogger.repositories.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        EmailVerificationRepository emailVerificationRepository,
        EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailService = emailService;
    }

    @Transactional
    public User registerNewUser(UserRegistrationDTO dto) {
        // user registration logic
        String lowerCaseEmail = dto.getEmail().toLowerCase();

        if (userRepository.findByEmail(lowerCaseEmail).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists: " + lowerCaseEmail);
        }

        User user = new User();
        user.setEmail(lowerCaseEmail);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUsername(dto.getUsername());

        User savedUser = userRepository.save(user);

        // generate verification token
        String token = generateSecureToken();
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(30);

        EmailVerification verification = new EmailVerification();
        verification.setUser(savedUser);
        verification.setToken(token);
        verification.setExpiresAt(expiresAt);
        emailVerificationRepository.save(verification);

        emailService.sendVerificationEmail(dto.getEmail(), "Account Verification", token);

        return savedUser;
    }

    private String generateSecureToken() {
        byte[] randomBytes = new byte[32];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Transactional
    public void verifyEmail(String token) {
        EmailVerification verification = emailVerificationRepository.findByToken(token)
            .orElseThrow(() -> new InvalidTokenException("The link is invalid."));

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            emailVerificationRepository.delete(verification);
            throw new TokenExpiredException("The link has expired.");
        }

        User user = verification.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);

        emailVerificationRepository.delete(verification);
    }

    @Transactional
    public User editProfileDetails(UserProfileEditDTO dto) {
        User user = userRepository.findById(dto.getId()).orElseThrow();

        Optional<User> optUser = userRepository.findByUsername(dto.getUsername());
        if ( optUser.isPresent() && (optUser.get().getId() != dto.getId()) ) {
            throw new UsernameTakenException("Username is already taken.");
        }

        user.setBio(dto.getBio());
        user.setDisplayName(dto.getDisplayName());
        user.setUsername(dto.getUsername());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User updateAvatar(Integer id, String imgUrl) {
        User foundUser = userRepository.findById(id).orElseThrow();
        foundUser.setPfp(imgUrl);
        foundUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(foundUser);
    }
    
}
