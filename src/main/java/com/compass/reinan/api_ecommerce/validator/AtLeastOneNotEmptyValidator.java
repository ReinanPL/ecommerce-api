package com.compass.reinan.api_ecommerce.validator;

import com.compass.reinan.api_ecommerce.domain.dto.sale.UpdatePatchItemSale;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, UpdatePatchItemSale> {

    @Override
    public boolean isValid(UpdatePatchItemSale value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean updateItemsValid = value.updateItems() != null && !value.updateItems().isEmpty();
        boolean removeItemsValid = value.removeItems() != null && !value.removeItems().isEmpty();
        return updateItemsValid || removeItemsValid;
    }
}