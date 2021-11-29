package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.model.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(buildErrorResponse(e.getErrors()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    private List<ErrorResponse> buildErrorResponse(List<FoodApiErrorReason> errors) {
        return errors.stream()
                .map(this::buildErrorResponse)
                .collect(Collectors.toList());
    }

    private ErrorResponse buildErrorResponse(FoodApiErrorReason errorReason) {
        return ErrorResponse.builder()
                .code(errorReason.getCode())
                .message(errorReason.getMessage())
                .build();
    }
}
