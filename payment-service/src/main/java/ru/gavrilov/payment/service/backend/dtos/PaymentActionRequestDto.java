package ru.gavrilov.payment.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentActionRequestDto {
    private Long clientId;
    private BigDecimal servicePrice;
    private String serviceName;
}
