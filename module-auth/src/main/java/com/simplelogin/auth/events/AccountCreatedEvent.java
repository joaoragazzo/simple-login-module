package com.simplelogin.auth.events;

public record AccountCreatedEvent(
    String email,
    String activationToken 
) {}
