package ru.gavrilov.project.client.service.backend.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestClientDto {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String email;
}
