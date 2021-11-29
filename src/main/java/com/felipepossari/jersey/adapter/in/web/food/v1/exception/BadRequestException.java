package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.model.ErrorResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException {

    private final List<ErrorResponse> errors;

    public BadRequestException(List<ErrorResponse> errors) {
        this.errors = errors;
    }
}
