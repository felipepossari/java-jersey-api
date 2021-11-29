package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.model.ErrorResponse;
import com.felipepossari.jersey.application.exception.ApplicationErrorReason;
import com.felipepossari.jersey.application.exception.ResourceNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    @Override
    public Response toResponse(ResourceNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(buildResponseErrors(e.getApplicationErrorReason()))
                .build();
    }

    private List<ErrorResponse> buildResponseErrors(ApplicationErrorReason applicationErrorReason) {
        return Collections.singletonList(
                ErrorResponse.builder()
                        .code(applicationErrorReason.getCode())
                        .message(applicationErrorReason.getMessage())
                        .build()
        );
    }
}
