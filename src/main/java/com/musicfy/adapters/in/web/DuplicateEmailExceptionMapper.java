package com.musicfy.adapters.in.web;

import com.musicfy.application.exception.DuplicateEmailException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.Map;

@Provider
public class DuplicateEmailExceptionMapper implements ExceptionMapper<DuplicateEmailException> {

    @Override
    public Response toResponse(DuplicateEmailException exception) {
        Map<String, Object> payload = Map.of(
                "timestamp", Instant.now().toString(),
                "status", 409,
                "error", "Conflict",
                "message", exception.getMessage()
        );

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(payload)
                .build();
    }
}

