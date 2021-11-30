package com.felipepossari.jersey.in.web.food.v1.controller;

import com.felipepossari.jersey.adapter.in.web.food.v1.FoodRequestValidator;
import com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason;
import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.application.domain.FoodType;
import com.felipepossari.jersey.base.request.FoodRequestTestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.felipepossari.jersey.base.DefaultConstants.INVALID_FOOD_NAME;
import static com.felipepossari.jersey.base.DefaultConstants.INVALID_FOOD_TYPE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FoodRequestValidatorTest {

    @InjectMocks
    FoodRequestValidator foodRequestValidator;

    @Test
    void validateShouldReturnNotErrorsIfRequestIsValid(){
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);
        assertTrue(errors.isEmpty());
    }

    @Test
    void validateShouldReturnNameEmptyErrorIfNameIsEmpty() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().name("").build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_NAME_EMPTY, errors.get(0));
    }

    @Test
    void validateShouldReturnNameInvalidErrorIfNameHasMoreThan50Characters() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest()
                .name(INVALID_FOOD_NAME)
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_NAME_INVALID_LENGTH, errors.get(0));
    }

    @Test
    void validateShouldReturnWeightInvalidErrorIfWeightIsZero() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().weight(0).build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_WEIGHT_INVALID, errors.get(0));
    }

    @Test
    void validateShouldReturnWeightInvalidErrorIfWeightIsNegative() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().weight(-1.0).build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_WEIGHT_INVALID, errors.get(0));
    }

    @Test
    void validateShouldReturnFoodTypeInvalidErrorIfFoodTypeIsEmpty() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().type("").build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_TYPE_INVALID, errors.get(0));
    }

    @Test
    void validateShouldReturnFoodTypeInvalidErrorIfFoodTypeIsInvalid() {
        FoodRequest request = FoodRequestTestBuilder.aFoodRequest().type(INVALID_FOOD_TYPE).build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_TYPE_INVALID, errors.get(0));
    }
}
