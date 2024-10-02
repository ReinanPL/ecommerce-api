package com.compass.reinan.api_ecommerce.service;

public interface UserRecoveryPasswordService {
    void sendEmailToResetUserPassword(String email);
    void resetUserPassword(String token, String newPassword, String confirmPassword);
}
