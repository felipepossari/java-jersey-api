package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {

    private final List<FoodApiErrorReason> errors;

    public BadRequestException(List<FoodApiErrorReason> errors) {
        this.errors = errors;
    }
}
