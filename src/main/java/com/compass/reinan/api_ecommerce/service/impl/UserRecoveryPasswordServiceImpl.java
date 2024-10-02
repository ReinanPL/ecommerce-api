package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.exception.PasswordInvalidException;
import com.compass.reinan.api_ecommerce.exception.PasswordTokenViolationException;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.SendMailService;
import com.compass.reinan.api_ecommerce.service.UserRecoveryPasswordService;
import com.compass.reinan.api_ecommerce.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserRecoveryPasswordServiceImpl implements UserRecoveryPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final SendMailService mailService;

    @Override
    @Transactional
    public void sendEmailToResetUserPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with emaill: '%s' not found", email)));

        var token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);

        var tokenInstant = Instant.now().plusSeconds(1800);
        user.setTokenExpirationDate(tokenInstant);

        userRepository.save(user);

        mailService.sendMail(token, email);
    }

    @Override
    @Transactional
    public void resetUserPassword(String token, String newPassword, String confirmPassword) {
        var user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found"));

        Optional.of(user.getTokenExpirationDate().isAfter(Instant.now()))
                .filter(expired ->expired)
                .orElseThrow(() -> new PasswordTokenViolationException("Expired token"));

        Optional.of(newPassword)
                .filter(p -> p.equals(confirmPassword))
                .orElseThrow(() -> new PasswordInvalidException("New password does not match with confirm password."));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }
}
