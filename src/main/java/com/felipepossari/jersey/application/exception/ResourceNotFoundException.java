package com.felipepossari.jersey.application.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
    private final ApplicationErrorReason applicationErrorReason;

    public ResourceNotFoundException(ApplicationErrorReason applicationErrorReason) {
        this.applicationErrorReason = applicationErrorReason;
    }
}
