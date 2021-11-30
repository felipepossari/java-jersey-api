package com.felipepossari.jersey.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ResourceRegisteredException extends RuntimeException{

    private final ApplicationErrorReason applicationErrorReason;
}
