package ru.gavrilov.payment.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentActionResponseDto {
    private long paymentActionId;
    private long clientId;
    private boolean isSuccess;
}
