package com.felipepossari.jersey.adapter.in.web.food.v1.exception.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private String code;
    private String message;
}
