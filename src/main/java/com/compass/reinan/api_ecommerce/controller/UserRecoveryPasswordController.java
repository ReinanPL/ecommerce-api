package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.ForgetPasswordRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.EmailUpdateRequest;
import org.springframework.http.ResponseEntity;

public interface UserRecoveryPasswordController {

    ResponseEntity<PasswordTokenResponse> sendEmailToResetUserPassword(EmailUpdateRequest emailDto);
    ResponseEntity<Void> resetUserPassword(String token, ForgetPasswordRequest reset);
}
