package com.compass.reinan.api_ecommerce.validator;

import com.compass.reinan.api_ecommerce.domain.dto.sale.request.UpdatePatchItemSaleRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneNotEmptyValidator implements ConstraintValidator<AtLeastOneNotEmpty, UpdatePatchItemSaleRequest> {

    @Override
    public boolean isValid(UpdatePatchItemSaleRequest value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean updateItemsValid = value.updateItems() != null && !value.updateItems().isEmpty();
        boolean removeItemsValid = value.removeItems() != null && !value.removeItems().isEmpty();
        return updateItemsValid || removeItemsValid;
    }
}