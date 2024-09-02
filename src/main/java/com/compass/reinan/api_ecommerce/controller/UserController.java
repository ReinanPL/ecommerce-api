package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.*;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;


import java.util.List;

@Tag(name = "User", description = "Operations related to managing users, including creating, updating, deleting, and retrieving user information.")
public interface UserController {

    @Operation(
            summary = "Create a new user",
            description = "Registers a new user in the system. Requires valid user details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{\"cpf\":\"12345678900\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"role\":\"USER\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User CPF already exists in the system.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User with this CPF already exists.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> saveUser(
            @Parameter(description = "Details of the user to be created.", required = true)
            @Valid
            UserCreateRequest userRequest
    );

    @Operation(
            summary = "Retrieve a user by CPF",
            description = "Fetches user details based on the provided CPF.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{\"cpf\":\"12345678900\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"role\":\"USER\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with the specified CPF not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> getUserByCpf(
            @Parameter(description = "The CPF of the user to retrieve.", required = true)
            String cpf
    );

    @Operation(
            summary = "Delete a user by CPF",
            description = "Deletes a user based on the provided CPF.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User deleted successfully."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with the specified CPF not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User not found.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteUserByCpf(
            @Parameter(description = "The CPF of the user to delete.", required = true)
            String cpf
    );

    @Operation(
            summary = "Update a user email",
            description = "Updates the email address of a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User email updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{\"cpf\":\"12345678900\",\"name\":\"John Doe\",\"email\":\"new.email@example.com\",\"address\":\"123 Main St\",\"role\":\"USER\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with the specified CPF not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Email address already registered in the system.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Email address already in use.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> updateUserEmail(
            @Parameter(description = "The CPF of the user whose email is to be updated.", required = true)
            String cpf,
            @Parameter(description = "Request object containing the new email address.", required = true)
            @Valid
            EmailUpdateRequest emailDto
    );

    @Operation(
            summary = "Update a user password",
            description = "Updates the password for a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Password updated successfully."
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Password does not match the confirmation or is invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Password mismatch or invalid format.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with the specified CPF not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid or malformed input fields.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid input fields.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> setUserPassword(
            @Parameter(description = "The CPF of the user whose password is to be updated.", required = true)
            String cpf,
            @Parameter(description = "Request object containing the new password and confirmation.", required = true)
            @Valid
            UpdatePasswordRequest password
    );

    @Operation(
            summary = "Update a user role",
            description = "Updates the role of a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User role updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{\"cpf\":\"12345678900\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"role\":\"ADMIN\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with the specified CPF not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "User already has the specified role.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User already has this role.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> updateUserRole(
            @Parameter(description = "The CPF of the user whose role is to be updated.", required = true)
            String cpf,
            @Parameter(description = "Request object containing the new role.", required = true)
            @Valid
            RoleUpdateRequest role
    );

    @Operation(
            summary = "Update a user address",
            description = "Updates the address of a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User address updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{\"cpf\":\"12345678900\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"456 Elm St\",\"role\":\"USER\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "User with the specified CPF not found.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User not found.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "422",
                            description = "Invalid input data provided.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Invalid data provided.\"}")
                            )
                    )
            }
    )
    ResponseEntity<UserResponse> updateUserAddress(
            @Parameter(description = "The CPF of the user whose address is to be updated.", required = true)
            String cpf,
            @Parameter(description = "Request object containing the new address details.", required = true)
            @Valid
            AddressRequest addressDto
    );

    @Operation(
            summary = "List all users",
            description = "Retrieves a list of all registered users in the system.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all registered users.",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[{\"cpf\":\"12345678900\",\"name\":\"John Doe\",\"email\":\"john.doe@example.com\",\"address\":\"123 Main St\",\"role\":\"USER\"}, {\"cpf\":\"09876543210\",\"name\":\"Jane Smith\",\"email\":\"jane.smith@example.com\",\"address\":\"456 Elm St\",\"role\":\"ADMIN\"}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserResponse.class)

                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<UserResponse>> getAllUsers();
}