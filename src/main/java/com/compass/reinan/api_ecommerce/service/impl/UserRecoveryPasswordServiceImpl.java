package com.compass.reinan.api_ecommerce.service.impl;

import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import com.compass.reinan.api_ecommerce.exception.PasswordInvalidException;
import com.compass.reinan.api_ecommerce.exception.PasswordTokenViolationException;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import com.compass.reinan.api_ecommerce.service.UserRecoveryPasswordService;
import com.compass.reinan.api_ecommerce.service.mapper.UserMapper;
import com.compass.reinan.api_ecommerce.util.DataUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserRecoveryPasswordServiceImpl implements UserRecoveryPasswordService {

    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final DataUtils dataUtils;

    @Override
    @Transactional
    public PasswordTokenResponse sendEmailToResetUserPassword(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        Instant tokenInstant = Instant.now().plusSeconds(1600);
        user.setTokenExpirationDate(tokenInstant);
        String formattedTokenInstant = dataUtils.formatToISO8601(tokenInstant);
        userRepository.save(user);
        return mapper.toResponsePassword(token, formattedTokenInstant);
    }

    @Override
    @Transactional
    public void resetUserPassword(String token, String newPassword, String confirmPassword) {
        var user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Optional.of(user.getTokenExpirationDate().isAfter(Instant.now()))
                .filter(expired ->expired)
                .orElseThrow(() -> new PasswordTokenViolationException("Invalid or expired token!"));
        Optional.of(newPassword)
                .filter(p -> p.equals(confirmPassword))
                .orElseThrow(() -> new PasswordInvalidException("New password does not match with confirm password."));
        user.setPassword(newPassword);
        user.setResetPasswordToken(null);
        user.setTokenExpirationDate(null);
        userRepository.save(user);
    }
}
