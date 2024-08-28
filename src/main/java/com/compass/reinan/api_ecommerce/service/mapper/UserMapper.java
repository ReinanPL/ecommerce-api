package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.AddressRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.UserCreateRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Address;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "address.state", source = "address.state", qualifiedByName = "toUpperCase")
    User toEntity(UserCreateRequest requestDto);

    UserResponse toResponse(User user);
    PasswordTokenResponse toResponsePassword(String token, String expirationDate);

    @Mapping(target = "state", source = "state", qualifiedByName = "toUpperCase")
    Address toEntityAddress(AddressRequest addressDto);

    @Named("toUpperCase")
    default String toUpperCase(String value) {
        return value.toUpperCase();
    }
}
