package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.ForgetPasswordRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.EmailUpdateRequest;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Password Reset", description = "Operations related to password reset, including requesting a password reset email and resetting the password using a token.")
public interface UserRecoveryPasswordController {

    @Operation(
            summary = "Send a password reset email",
            description = "Sends a password reset email to the specified address. This email contains a link for resetting the password.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password reset email sent successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PasswordTokenResponse.class),
                                    examples = @ExampleObject(value = "{\"token\":\"example-token\",\"message\":\"Password reset link sent to your email.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Email address not found in the system.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Email not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid email format or unable to process the request.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid email format.\"}")
                            )
                    )
            }
    )
    ResponseEntity<PasswordTokenResponse> sendEmailToResetUserPassword(
            @Parameter(description = "Request object containing the email address for which the password reset link will be sent.", required = true)
            EmailUpdateRequest emailDto
    );

    @Operation(
            summary = "Reset the user's password using a token",
            description = "Resets the user's password using the provided token and new password. The token must be valid and not expired.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Password reset successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Passwords do not match or invalid password.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Passwords do not match.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Token has expired or is invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Token expired.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Token not found in the system.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Token not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> resetUserPassword(
            @Parameter(description = "The token provided in the password reset email.", required = true)
            String token,
            @Parameter(description = "Request object containing the new password and confirmation.", required = true)
            ForgetPasswordRequest reset
    );
}