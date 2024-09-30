package ru.gavrilov.project.client.service.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.project.client.service.backend.dtos.ClientDetailsDto;
import ru.gavrilov.project.client.service.backend.dtos.RequestClientDto;
import ru.gavrilov.project.client.service.backend.dtos.ResponseClientDto;
import ru.gavrilov.project.client.service.backend.dtos.ResponseUpdatedClient;
import ru.gavrilov.project.client.service.backend.services.ClientService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/new-client")
    public ResponseClientDto createNewClient(@RequestBody RequestClientDto requestClientDto){
        return clientService.createNewClient(requestClientDto);
    }

    @GetMapping("/details")
    public ClientDetailsDto getClientDetails(@RequestHeader Long clientId){
        return clientService.getDetails(clientId);
    }

    @PatchMapping("/update-profile")
    public ResponseUpdatedClient updateClientDetails(@RequestBody RequestClientDto requestClientDto){
        return clientService.update(requestClientDto);
    }

}
