package ru.gavrilov.project.subscription.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaEventDto {
    private Long clientId;
    private String title;
    private String message;
    private String service;
}
