package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.security.UserLoginRequest;
import com.compass.reinan.api_ecommerce.domain.dto.security.UserTokenResponse;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Authentication", description = "Contains operations related to authenticating user.")
public interface AuthenticationController {

    @Operation(summary = "Authenticate a user", description = "Resource for authenticating an existing user and generating a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User authenticated successfully. Returns a JWT token.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserTokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials. User authentication failed.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid fields. User authentication failed.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<UserTokenResponse> authenticationUser(UserLoginRequest loginRequest);
}
