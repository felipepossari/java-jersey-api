package com.felipepossari.jersey.application.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrorReason {

    RESOURCE_NOT_FOUND("C001", "Resource not found"),
    RESOURCE_REGISTERED("C002", "Resource already registered");

    private final String code;
    private final String message;

    ApplicationErrorReason(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
