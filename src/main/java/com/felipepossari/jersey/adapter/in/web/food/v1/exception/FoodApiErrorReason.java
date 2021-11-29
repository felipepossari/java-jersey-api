package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import lombok.Getter;

@Getter
public enum FoodApiErrorReason {

    FOOD_NAME_EMPTY("R001", "Food name must not be null or empty"),
    FOOD_NAME_INVALID_LENGTH("R002", "Food name must not have more than 50 characters"),
    FOOD_WEIGHT_INVALID("R003", "Food weight invalid"),
    FOOD_TYPE_INVALID("R003", "Food type invalid");

    private final String code;
    private final String message;

    FoodApiErrorReason(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
