package com.musicfy.adapters.in.web;

import org.hibernate.exception.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.time.Instant;
import java.util.Map;

@Provider
public class PersistenceConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        String message = "Database constraint violation";
        if (exception.getSQLException() != null && exception.getSQLException().getMessage() != null) {
            message = exception.getSQLException().getMessage();
        }

        Map<String, Object> payload = Map.of(
                "timestamp", Instant.now().toString(),
                "status", 409,
                "error", "Conflict",
                "message", message
        );

        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(payload)
                .build();
    }
}

