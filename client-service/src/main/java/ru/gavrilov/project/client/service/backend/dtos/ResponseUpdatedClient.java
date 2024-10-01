package ru.gavrilov.project.client.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseUpdatedClient {
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime updatedAt;
}
