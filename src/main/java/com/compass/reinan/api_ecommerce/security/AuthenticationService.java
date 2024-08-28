package com.compass.reinan.api_ecommerce.security;

import com.compass.reinan.api_ecommerce.domain.dto.security.UserLoginRequest;
import com.compass.reinan.api_ecommerce.domain.dto.security.UserTokenResponse;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserTokenResponse generateToken(User user){
        var now = Instant.now();
        var expiresIn = 300L;

        var scopes = user.getRole();

        var claims = JwtClaimsSet.builder()
                .issuer("reinan-webservices")
                .subject(user.getCpf())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new UserTokenResponse(jwtValue, expiresIn);
    }

    public User checkUserToken(UserLoginRequest loginRequest) {
        var user = userRepository.findByCpf(loginRequest.cpf())
                .orElseThrow(() -> new BadCredentialsException("User not found: " + loginRequest.cpf()));

        Optional.of(user.isLoginCorrect(loginRequest, passwordEncoder))
                .filter(exists ->exists)
                .orElseThrow(() -> new BadCredentialsException("Password is invalid!"));
        return user;
    }
}
