package ru.gavrilov.project.subscription.service.backend.errors;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
