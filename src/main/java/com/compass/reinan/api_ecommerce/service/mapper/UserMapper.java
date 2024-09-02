package com.compass.reinan.api_ecommerce.service.mapper;

import com.compass.reinan.api_ecommerce.domain.dto.user.request.AddressRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.request.UserCreateRequest;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.PasswordTokenResponse;
import com.compass.reinan.api_ecommerce.domain.dto.user.response.UserResponse;
import com.compass.reinan.api_ecommerce.domain.entity.Address;
import com.compass.reinan.api_ecommerce.domain.entity.User;
import com.compass.reinan.api_ecommerce.util.DateUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "address.state", source = "address.state", qualifiedByName = "toUpperCase")
    User toEntity(UserCreateRequest requestDto);

    UserResponse toResponse(User user);

    PasswordTokenResponse toResponsePassword(String token, Instant expirationDate);

    @Mapping(target = "state", source = "state", qualifiedByName = "toUpperCase")
    @Mapping(target = "id", ignore = true)
    Address toEntityAddress(AddressRequest addressDto);

    @Named("toUpperCase")
    default String toUpperCase(String value) {
        return value.toUpperCase();
    }

    default String formatInstantToISO8601(Instant instant) {
        return DateUtils.formatToISO8601(instant);
    }
}
