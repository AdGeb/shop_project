package com.app.service;

import com.app.model.security.User;
import com.app.model.security.VerificationToken;
import com.app.repository.security.UserRepository;
import com.app.repository.security.repository.VerificationTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
    }

    public User getUser(String token) {
        return verificationTokenRepository.findByToken(token).getUser();
    }

    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public void saveRegisteredUserAfterConfirmation(User user) {
        userRepository.save(user);
    }

    public VerificationToken createVerificationToken(String token, User user) {
        return verificationTokenRepository.save(VerificationToken.builder()
                .user(user)
                .token(token)
                .expirationDateTime(LocalDateTime.now().plusMinutes(1))
                .build());
    }

    public void registrationConfirm(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            throw new IllegalArgumentException("TOKEN IS NOT VALID");
        }

        if (verificationToken.getExpirationDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("TOKEN IS EXPIRED");
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        saveRegisteredUserAfterConfirmation(user);
    }

}
