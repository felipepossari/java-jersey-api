package com.felipepossari.jersey.in.web.food.v1.controller;

import com.felipepossari.jersey.adapter.in.web.food.v1.FoodRequestValidator;
import com.felipepossari.jersey.adapter.in.web.food.v1.exception.FoodApiErrorReason;
import com.felipepossari.jersey.adapter.in.web.food.v1.request.FoodRequest;
import com.felipepossari.jersey.application.domain.FoodType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class FoodRequestValidatorTest {

    @InjectMocks
    FoodRequestValidator foodRequestValidator;

    @Test
    void validateShouldReturnNameEmptyErrorIfNameIsEmpty() {
        FoodRequest request = FoodRequest.builder()
                .type(FoodType.MEAT.name())
                .weight(1.0)
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_NAME_EMPTY, errors.get(0));
    }

    @Test
    void validateShouldReturnNameInvalidErrorIfNameHasMoreThan50Characters() {
        FoodRequest request = FoodRequest.builder()
                .name("Orange Orange Orange Orange Orange Orange Orange Or")
                .type(FoodType.MEAT.name())
                .weight(1.0)
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_NAME_INVALID_LENGTH, errors.get(0));
    }

    @Test
    void validateShouldReturnWeightInvalidErrorIfWeightIsZero() {
        FoodRequest request = FoodRequest.builder()
                .name("Orange")
                .type(FoodType.MEAT.name())
                .weight(0)
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_WEIGHT_INVALID, errors.get(0));
    }

    @Test
    void validateShouldReturnWeightInvalidErrorIfWeightIsNegative() {
        FoodRequest request = FoodRequest.builder()
                .name("Orange")
                .type(FoodType.MEAT.name())
                .weight(-1.0)
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_WEIGHT_INVALID, errors.get(0));
    }

    @Test
    void validateShouldReturnFoodTypeInvalidErrorIfFoodTypeIsEmpty() {
        FoodRequest request = FoodRequest.builder()
                .name("Orange")
                .weight(1.0)
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_TYPE_INVALID, errors.get(0));
    }

    @Test
    void validateShouldReturnFoodTypeInvalidErrorIfFoodTypeIsInvalid() {
        FoodRequest request = FoodRequest.builder()
                .name("Orange")
                .weight(1.0)
                .type("FROZEN FOOD")
                .build();

        List<FoodApiErrorReason> errors = foodRequestValidator.validate(request);

        assertFalse(errors.isEmpty());
        assertEquals(FoodApiErrorReason.FOOD_TYPE_INVALID, errors.get(0));
    }
}
