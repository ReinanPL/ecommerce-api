package com.compass.reinan.api_ecommerce.domain.entity;

import com.compass.reinan.api_ecommerce.domain.dto.security.UserLoginRequest;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="tb_users")
public class User implements Serializable {

    @Id
    @Column(name = "cpf", unique = true, nullable = false, length =11)
    private String cpf;
    @Column(name = "first_name", nullable = false, length =50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length =50)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false, length =100)
    private String email;
    @Column(name = "password", nullable = false, length =200)
    private String password;
    @Column(name = "role", nullable = false, length = 7)
    @Enumerated(EnumType.STRING)
    private Role role = Role.CLIENT;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "address_id")
    private Address address;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Sale> sales = new ArrayList<>();

    @Column(name = "reset_password_token")
    private String resetPasswordToken;
    @Column(name = "token_expiration_date")
    private Instant tokenExpirationDate;


    public boolean isLoginCorrect(UserLoginRequest loginRequest, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(loginRequest.password(), this.password);
    }
}
