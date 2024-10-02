package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.UserController;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.*;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.service.UserService;
import com.compass.reinan.api_ecommerce.util.MediaType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PostMapping(
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<UserResponse> saveUser(@RequestBody @Valid CreateUserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userRequest));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @GetMapping(value = "/{cpf}",
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<UserResponse> getUserByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(userService.getUserByCpf(cpf));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping(value = "/{cpf}")
    public ResponseEntity<Void> deleteUserByCpf(@PathVariable String cpf) {
        userService.deleteUserByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @PatchMapping(value = "/{cpf}",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<UserResponse> updateUserEmail(@PathVariable String cpf, @RequestBody @Valid UpdateEmailRequest emailDto) {
        return ResponseEntity.ok(userService.updateUserEmail(cpf, emailDto.newEmail()));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @PatchMapping(value = "/{cpf}/password",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<Void> setUserPassword(@PathVariable String cpf, @RequestBody @Valid UpdatePasswordRequest password){
        userService.updateUserPassword(cpf, password.oldPassword(), password.newPassword(), password.confirmPassword());
        return ResponseEntity.noContent().build();
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PatchMapping(value = "/{cpf}/role",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable String cpf, @RequestBody @Valid UpdateRoleRequest role) {
        return ResponseEntity.ok().body(userService.updateUserRole(cpf, role.role()));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN') or (hasAuthority('SCOPE_CLIENT') and #cpf == authentication.name)")
    @PatchMapping(value = "/{cpf}/address",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<UserResponse> updateUserAddress(@PathVariable String cpf, @RequestBody @Valid UpdateAddressRequest addressDto) {
        return ResponseEntity.ok(userService.updateUserAddress(cpf, addressDto));
    }

    @Override
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping(
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
