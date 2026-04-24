package com.musicfy.application.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class UserEventPublisher {

    @Inject
    ObjectMapper objectMapper;

    @Channel("user-created-out")
    Emitter<String> emitter;

    public void publishUserCreated(UserCreatedEvent event) {
        try {
            emitter.send(objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            throw new RuntimeException("Error publishing user-created", e);
        }
    }
}
