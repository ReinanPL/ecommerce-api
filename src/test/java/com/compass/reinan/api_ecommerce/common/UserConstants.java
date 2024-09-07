package com.compass.reinan.api_ecommerce.common;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.UpdateAddressRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.CreateUserRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Address;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.domain.entity.enums.Role;

import java.util.Collections;

public class UserConstants {
    public static final UpdateAddressRequest ADDRESS_UPDATE_REQUEST = new UpdateAddressRequest("21372278", "São Paulo", "SP", "New Street", "125", "New complement");
    public static final Address ADDRESS_UPDATE = new Address(1L, "21372278", "São Paulo", "SP", "New Street", "125", "New complement");
    public static final UpdateAddressRequest ADDRESS_REQUEST = new UpdateAddressRequest("12345678", "Salvador", "BA", "New Street", "123", "New complement");
    public static final Address ADDRESS = new Address(1L, "12345678", "Salvador", "BA", "New Street", "123", "New complement");

    public static final CreateUserRequest USER_REQUEST = new CreateUserRequest("52624127003", "John", "Doe", "johndoe@email.com", "123456", "123456", ADDRESS_REQUEST);
    public static final UserResponse USER_RESPONSE = new UserResponse("52624127003", "johndoe@email.com", "John", "Doe", ADDRESS, "CLIENT");
    public static final UserResponse NEW_USER_ROLE_RESPONSE = new UserResponse("52624127003", "johndoe@email.com", "John", "Doe", ADDRESS, "ADMIN");
    public static final UserResponse NEW_USER_EMAIL_RESPONSE = new UserResponse("52624127003", "john@email.com", "John", "Doe", ADDRESS, "CLIENT");
    public static final UserResponse NEW_USER_ADDRESS_RESPONSE = new UserResponse("52624127003", "john@email.com", "John", "Doe", ADDRESS_UPDATE, "CLIENT");

    public static final User USER_EXISTING = new User("52624127003", "John", "Doe", "johndoe@email.com", "123456", Role.CLIENT, ADDRESS, Collections.emptyList(), null, null);
    public static final User USER_ADMIN = new User("93619622051", "Lean", "Doe", "leandoe@email.com", "123456", Role.ADMIN, ADDRESS, Collections.emptyList(), null, null);
    public static final User USER_ADDRESS_UPDATE = new User("52624127003", "John", "Doe", "johndoe@email.com", "123456", Role.CLIENT, ADDRESS_UPDATE, Collections.emptyList(), null, null);

    public static final User NEW_USER_EMAIL = new User("52624127003", "John", "Doe", "john@email.com", "123456", Role.CLIENT, ADDRESS, Collections.emptyList(), null, null);
    public static final User NEW_USER_ROLE = new User("52624127003", "John", "Doe", "johndoe@email.com", "123456", Role.CLIENT, ADDRESS, Collections.emptyList(), null, null);


    public static final User USER_PASSWORD_ENCRYPTED = new User("52624127003", "John", "Doe", "johndoe@email.com", "$2a$12$QJPRdVvwJGjqempA7cv8qecnOSjxGIKKubGt9dle2cIGkvmQQqOcS", Role.CLIENT, ADDRESS, Collections.emptyList(), null, null);
    public static final String EXISTING_CPF = "52624127003";
    public static final String EXISTING_CPF_ADMIN = "93619622051";
    public static final String NON_EXISTING_CPF = "12345678901";
    public static final String NEW_EMAIL = "john@email.com";

}