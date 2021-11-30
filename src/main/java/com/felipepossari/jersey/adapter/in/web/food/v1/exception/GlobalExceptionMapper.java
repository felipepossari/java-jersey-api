package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.logging.Logger;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = Logger.getLogger(GlobalExceptionMapper.class.getName());

    @Override
    public Response toResponse(Exception e) {
        log.severe("Unknown error happened. Error: " + e.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(buildErrorResponse(FoodApiErrorReason.UNKNOWN_REASON))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private Object buildErrorResponse(FoodApiErrorReason unknownReason) {
        return Collections.singletonList(
                ErrorResponse.builder()
                        .code(unknownReason.getCode())
                        .message(unknownReason.getMessage())
                        .build());
    }
}
