package com.felipepossari.jersey.adapter.in.web.food.v1.exception;

import com.felipepossari.jersey.adapter.in.web.food.v1.exception.model.ErrorResponse;
import com.felipepossari.jersey.application.exception.ApplicationErrorReason;
import com.felipepossari.jersey.application.exception.ResourceRegisteredException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;

@Provider
public class ResourceRegisteredExceptionMapper implements ExceptionMapper<ResourceRegisteredException> {
    @Override
    public Response toResponse(ResourceRegisteredException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(buildResponseErrors(e.getApplicationErrorReason()))
                .type(MediaType.APPLICATION_JSON)
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
