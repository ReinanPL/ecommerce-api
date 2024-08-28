package com.compass.reinan.api_ecommerce.controller;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.*;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Contains all operations related to resources for registering, deleting, editing and reading a User.")
public interface UserController {

    @Operation(summary = "Create a new user", description = "Resource for creating a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description
                            = "Resource created with success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "409", description = "User cpf already registered in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserCreateRequest userRequest);

    @Operation(summary = "Retrieve a user by CPF", description = "Retrieve a user by CPF",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf);

    @Operation(summary = "Delete a user by CPF", description = "Delete a user by CPF",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Resource deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<Void> deleteUserByCpf(@PathVariable String cpf);

    @Operation(summary = "Modify a user email", description = "Modify a user email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User cpf not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "Email already registered in the system",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description
                            = "Resource not processed due to invalid input data", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            })
    ResponseEntity<UserResponse> updateUserEmail(@PathVariable String cpf, @RequestBody @Valid EmailUpdateRequest emailDto);

    @Operation(summary = "Update password", description = "Update client password.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password updated successfully",
                            content = @Content(schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Password does not match",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "User cpf to change password not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "invalid or bad formatted fields",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            })
    ResponseEntity<Void> setUserPassword(@PathVariable String cpf, @RequestBody @Valid UpdatePasswordRequest password);

    @Operation(summary = "Modify a user role", description = "Modify a user role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User cpf not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "409", description = "User already have the role",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description
                            = "Resource not processed due to invalid input data", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            })

    ResponseEntity<UserResponse> updateUserRole(@PathVariable String cpf, @RequestBody RoleUpdateRequest role);

    @Operation(summary = "Modify a user address", description = "Modify a user address",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "404", description = "User cpf not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description
                            = "Resource not processed due to invalid input data", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
            })
    ResponseEntity<UserResponse> updateUserAddress(String cpf,  AddressRequest addressDto);

    @Operation(summary = "List all users", description = "List all registered users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of all registered users", content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
            })
    ResponseEntity<List<UserResponse>> getAllUsers();

}
