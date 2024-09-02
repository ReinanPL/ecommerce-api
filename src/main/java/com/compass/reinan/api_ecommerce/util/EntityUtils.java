package com.compass.reinan.api_ecommerce.util;

import com.compass.reinan.api_ecommerce.exception.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class EntityUtils<T, ID extends Comparable<ID>> {

    public static <T, ID extends Comparable<ID>> T getEntityOrThrow(ID id, Class<T> entityClass, JpaRepository<T, ID> repository) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("%s not found with id: %s", entityClass.getSimpleName(), id)));
    }
}
