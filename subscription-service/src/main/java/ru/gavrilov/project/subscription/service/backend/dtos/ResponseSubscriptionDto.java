package ru.gavrilov.project.subscription.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSubscriptionDto {
    private Long subscriptionId;
    private String subscriptionTitle;
    private BigDecimal price;
    private String status;
}
