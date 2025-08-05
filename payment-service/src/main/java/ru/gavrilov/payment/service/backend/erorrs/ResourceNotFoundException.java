package ru.gavrilov.payment.service.backend.erorrs;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
