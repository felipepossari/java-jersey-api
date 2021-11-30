package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import com.felipepossari.jersey.application.domain.FoodType;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public enum FoodApiErrorReason {

    FOOD_NAME_EMPTY("R001", "Food name must not be null or empty"),
    FOOD_NAME_INVALID_LENGTH("R002", "Food name must not have more than 50 characters"),
    FOOD_WEIGHT_INVALID("R003", "Food weight invalid"),
    FOOD_TYPE_INVALID("R003", "Food type invalid. Values: "
            + Arrays.stream(FoodType.values()).map(Enum::name).collect(Collectors.joining(", "))),
    UNKNOWN_REASON("R999", "Unknown error");

    private final String code;
    private final String message;

    FoodApiErrorReason(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
