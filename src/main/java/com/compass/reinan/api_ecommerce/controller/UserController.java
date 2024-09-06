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
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


import java.util.List;

@Tag(name = "User", description = "Operations related to managing users, including creating, updating, deleting, and retrieving user information.")
public interface UserController {

    @Operation(
            summary = "Create a new user",
            description = "Registers a new user in the system. Requires valid user details.",
            requestBody = @RequestBody(
                    description = "Request body for a new user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateEmailRequest.class),
                            examples = @ExampleObject(value = "{ \"cpf\": \"43721493010\", \"first_name\": \"Joao\", \"last_name\": \"Silva\", \"email\": \"joao.silva@example.com\", \"password\": \"123456\", \"confirm_password\": \"123456\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Flores\", \"number\": \"123\", \"complement\": \"Apartamento 101\" }, \"role\":\"CLIENT\" }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{ \"cpf\": \"43721493010\", \"first_name\": \"Joao\", \"last_name\": \"Silva\", \"email\": \"joao.silva@example.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Flores\", \"number\": \"123\", \"complement\": \"Apartamento 101\" }, \"role\":\"CLIENT\" }")
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
    ResponseEntity<UserResponse> saveUser(CreateUserRequest userRequest);

    @Operation(
            summary = "Retrieve a user by CPF",
            description = "Fetches user details based on the provided CPF.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user to retrieve.", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User retrieved successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{ \"cpf\": \"43721493010\", \"first_name\": \"John\", \"last_name\": \"Joao\", \"Silva\": \"joao.silva@example.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Flores\", \"number\": \"123\", \"complement\": \"Apartamento 101\" }, \"role\":\"CLIENT\" }")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
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
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user to delete.", required = true)
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "User deleted successfully."
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
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
                            description = "User has related sales and cannot be deleted.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"User has related sales.\"}")
                            )
                    )
            }
    )
    ResponseEntity<Void> deleteUserByCpf(String cpf);

    @Operation(
            summary = "Update a user email",
            description = "Updates the email address of a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose email is to be updated.", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for a email to the user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateEmailRequest.class),
                            examples = @ExampleObject(value = "{ \"new_email\": \"novo.email@example.com\"}")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User email updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{ \"cpf\": \"43721493010\", \"first_name\": \"Joao\", \"last_name\": \"Silva\", \"email\": \"novo.email@gmail.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Flores\", \"number\": \"123\", \"complement\": \"Apartamento 101\" }, \"role\":\"CLIENT\" }")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
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
    ResponseEntity<UserResponse> updateUserEmail(String cpf, UpdateEmailRequest emailDto);

    @Operation(
            summary = "Update a user password",
            description = "Updates the password for a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose password is to be updated.", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for a new password for the user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdatePasswordRequest.class),
                            examples = @ExampleObject(value = "{ \"old_password\": \"123456\", \"new_password\": \"12345678\", \"confirm_password\": \"12345678\" }")
                    )
            ),
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
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
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
    ResponseEntity<Void> setUserPassword(String cpf, UpdatePasswordRequest password);

    @Operation(
            summary = "Update a user role",
            description = "Updates the role of a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose role is to be updated.", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for update the user role.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateRoleRequest.class),
                            examples = @ExampleObject(value = "{ \"role\": \"ADMIN\" }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User role updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{ \"cpf\": \"43721493010\", \"first_name\": \"Joao\", \"last_name\": \"Silva\", \"email\": \"joao.silva@example.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Flores\", \"number\": \"123\", \"complement\": \"Apartamento 101\" }, \"role\" : \"ADMIN\" }")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
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
    ResponseEntity<UserResponse> updateUserRole(String cpf, UpdateRoleRequest role);

    @Operation(
            summary = "Update a user address",
            description = "Updates the address of a user identified by CPF.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(name = "cpf", description = "The CPF of the user whose address is to be updated.", required = true)
            },
            requestBody = @RequestBody(
                    description = "Request body for new address to the user.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateAddressRequest.class),
                            examples = @ExampleObject(value = "{ \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Margaridas\", \"number\": \"122\", \"complement\": \"Apartamento 101\" }")
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User address updated successfully.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserResponse.class),
                                    examples = @ExampleObject(value = "{ \"cpf\": \"43721493010\", \"first_name\": \"Joao\", \"last_name\": \"Silva\", \"email\": \"joao.silva@example.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Margaridas\", \"number\": \"122\", \"complement\": \"Apartamento 101\" } }")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
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
    ResponseEntity<UserResponse> updateUserAddress(String cpf, UpdateAddressRequest addressDto);

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
                                    examples = @ExampleObject(value = "[{ \"cpf\": \"43721493010\", \"first_name\": \"Joao\", \"last_name\": \"Silva\", \"email\": \"joao.silva@example.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Margaridas\", \"number\": \"122\", \"complement\": \"Apartamento 101\",\"role\":\"ADMIN\"} }, { \"cpf\": \"24492471065\", \"first_name\": \"Lucas\", \"last_name\": \"Silva\", \"email\": \"lucas.silva@gmail.com\", \"address\": { \"cep\": \"01234567\", \"city\": \"São Paulo\", \"state\": \"SP\", \"street\": \"Rua das Margaridas\", \"number\": \"122\", \"complement\": \"Apartamento 101\",\"role\":\"CLIENT\"}}]"),
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = UserResponse.class)

                                    )
                            )
                    ),@ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. The user is not authenticated or the authentication credentials are missing/invalid.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class),
                            examples = @ExampleObject(value = "{\"error\":\"Unauthorized access.\"}")
                    )
            ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden. The authenticated user does not have permission to access this resource.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class),
                                    examples = @ExampleObject(value = "{\"error\":\"Access denied.\"}")
                            )
                    )
            }
    )
    ResponseEntity<List<UserResponse>> getAllUsers();
}