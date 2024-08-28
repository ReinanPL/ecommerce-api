package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.ForgetPasswordRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.EmailUpdateRequest;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Password Reset", description = "Contains operations related to password reset.")
public interface UserRecoveryPasswordController {

    @Operation(summary = "Send an email to reset the user's password.", description = "Sends an email to the specified email address containing a password reset link.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PasswordTokenResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Email not found in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid email",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<PasswordTokenResponse> sendEmailToResetUserPassword(EmailUpdateRequest emailDto);

    @Operation(summary = "Reset the user's password using a token.", description = "Resets the user's password using the provided token and new password.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password reset successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Passwords don't match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "401", description = "Token expired",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Token not found in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<Void> resetUserPassword(String token, ForgetPasswordRequest reset);
}
