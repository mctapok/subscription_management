package ru.gavrilov.project.subscription.service.backend.errors;

public class RepeatSubscriptionException extends RuntimeException {
    public RepeatSubscriptionException(String message) {
        super(message);
    }
}
