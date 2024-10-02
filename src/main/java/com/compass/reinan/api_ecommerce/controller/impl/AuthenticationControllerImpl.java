package com.compass.reinan.api_ecommerce.controller.impl;

import com.compass.reinan.api_ecommerce.controller.AuthenticationController;
import com.compass.reinan.api_ecommerce.domain.dto.security.UserLoginRequest;
import com.compass.reinan.api_ecommerce.domain.dto.security.UserTokenResponse;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.security.AuthenticationService;
import com.compass.reinan.api_ecommerce.util.MediaType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationControllerImpl implements AuthenticationController {

    private final AuthenticationService authenticationService;

    @Override
    @PostMapping(value = "/login",
            consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
            produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
    public ResponseEntity<UserTokenResponse> authenticationUser(@RequestBody @Valid UserLoginRequest loginRequest) {
        User userChecked = authenticationService.checkUserToken(loginRequest);
        UserTokenResponse responseToken = authenticationService.generateToken(userChecked);
        return ResponseEntity.ok().body(responseToken);
    }
}
