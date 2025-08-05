package ru.gavrilov.payment.service.backend.erorrs;

public class RepeatSubscriptionException extends RuntimeException {
    public RepeatSubscriptionException(String message) {
        super(message);
    }
}
