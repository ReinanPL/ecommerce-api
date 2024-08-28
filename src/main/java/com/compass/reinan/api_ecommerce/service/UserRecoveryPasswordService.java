package com.compass.reinan.api_ecommerce.service;

import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;

public interface UserRecoveryPasswordService {
    PasswordTokenResponse sendEmailToResetUserPassword(String email);
    void resetUserPassword(String token, String newPassword, String confirmPassword);
}
