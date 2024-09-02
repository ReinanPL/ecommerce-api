package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.security.UserLoginRequest;
import com.compass.reinan.api_ecommerce.domain.dto.security.UserTokenResponse;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Authentication", description = "Operations related to user authentication and JWT token generation.")
public interface AuthenticationController {

    @Operation(
            summary = "Authenticate a user",
            description = "Authenticates an existing user by validating their credentials and generates a JWT token for successful authentication. The token can be used to access protected resources.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User authenticated successfully. Returns a JWT token.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserTokenResponse.class),
                                    examples = @ExampleObject(value = "{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid credentials. User authentication failed.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid username or password.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data. User authentication failed due to invalid fields.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid request fields. Ensure username and password are provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<UserTokenResponse> authenticationUser(
            @Parameter(description = "Login credentials for user authentication. Must include username and password.", required = true)
            UserLoginRequest loginRequest
    );
}
