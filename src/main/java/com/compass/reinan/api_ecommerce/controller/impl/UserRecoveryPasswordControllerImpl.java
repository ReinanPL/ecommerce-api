package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.UserRecoveryPasswordController;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.ForgetPasswordEmailRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.ForgetPasswordRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.UpdateEmailRequest;
import com.compass.reinan.api_ecommerce.service.UserRecoveryPasswordService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/resetPassword")
public class UserRecoveryPasswordControllerImpl implements UserRecoveryPasswordController {

    private final UserRecoveryPasswordService passwordService;

    @PostMapping("/sendEmail")
    public ResponseEntity<Void> sendEmailToResetUserPassword(@RequestBody @Valid ForgetPasswordEmailRequest emailRequest) {
        passwordService.sendEmailToResetUserPassword(emailRequest.email());
        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/reset/")
    public ResponseEntity<Void> resetUserPassword(@RequestParam(required = true) String token, @RequestBody @Valid ForgetPasswordRequest reset) {
        passwordService.resetUserPassword(token, reset.newPassword(), reset.confirmPassword());
        return ResponseEntity.noContent().build();
    }
}
