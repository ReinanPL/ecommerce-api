package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.UserController;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.*;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid UserCreateRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequest));
    }

    @Override
    @GetMapping("/{cpf}")
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(userService.getUserByCpf(cpf));
    }

    @Override
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deleteUserByCpf(@PathVariable String cpf) {
        userService.deleteUserByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{cpf}")
    public ResponseEntity<UserResponse> updateUserEmail(@PathVariable String cpf, @RequestBody @Valid EmailUpdateRequest emailDto) {
        return ResponseEntity.ok(userService.updateUserEmail(cpf, emailDto.newEmail()));
    }

    @Override
    @PatchMapping("/{cpf}/password")
    public ResponseEntity<Void> setUserPassword(@PathVariable String cpf, @RequestBody @Valid UpdatePasswordRequest password){
        userService.updateUserPassword(cpf, password.oldPassword(), password.newPassword(), password.confirmPassword());
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{cpf}/role")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable String cpf, @RequestBody @Valid RoleUpdateRequest role) {
        return ResponseEntity.ok().body(userService.updateUserRole(cpf, role.role()));
    }

    @Override
    @PatchMapping("/{cpf}/address")
    public ResponseEntity<UserResponse> updateUserAddress(@PathVariable String cpf, @RequestBody @Valid AddressRequest addressDto) {
        return ResponseEntity.ok(userService.updateUserAddress(cpf, addressDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
