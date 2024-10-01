package ru.gavrilov.payment.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreatedDto {
    private Long accountId;
    private Long clientId;
}
