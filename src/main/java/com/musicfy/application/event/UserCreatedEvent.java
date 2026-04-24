package com.musicfy.application.event;

public record UserCreatedEvent(Long id, String username, String email) {
}

