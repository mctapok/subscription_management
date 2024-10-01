package ru.gavrilov.project.client.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionEventDto {
    private Long clientId;
    private String title;
    private String service;
    private String message;
}
