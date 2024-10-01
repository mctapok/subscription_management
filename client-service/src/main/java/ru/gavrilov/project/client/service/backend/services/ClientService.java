package ru.gavrilov.project.client.service.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilov.project.client.service.backend.dtos.*;
import ru.gavrilov.project.client.service.backend.entities.Client;
import ru.gavrilov.project.client.service.backend.entities.EventHistory;
import ru.gavrilov.project.client.service.backend.kafka.KafkaProducer;
import ru.gavrilov.project.client.service.backend.repositories.ClientRepository;
import ru.gavrilov.project.client.service.backend.repositories.EventHistoryRepository;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final EventHistoryRepository eventHistoryRepository;
    private final KafkaProducer kafkaProducer;

    private static <E, D> D mapEntityToDto(E entity, Function<E, D> mapper) {
        return mapper.apply(entity);
    }

    public ResponseClientDto createNewClient(RequestClientDto requestClientDto) {
        Client newClient = new Client(requestClientDto.getClientId(), requestClientDto.getFirstName(), requestClientDto.getLastName(), requestClientDto.getEmail());
        kafkaProducer.sendClientCreated(new ClientCreatedDto(newClient.getClientId()));
        newClient = clientRepository.save(newClient);

        return mapEntityToDto(newClient, nC -> new ResponseClientDto(nC.getFirstName()));
    }

    public ClientDetailsDto getDetails(Long clientId) {
        Client client = clientRepository.findByClientId(clientId).orElseThrow(() -> new RuntimeException("Client not found"));
        return mapEntityToDto(client, c -> new ClientDetailsDto(
                c.getClientId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getCreatedAt())
        );
    }

    public ResponseUpdatedClient update(RequestClientDto requestClientDto) {
        Optional<Client> client = clientRepository.findByClientId(requestClientDto.getClientId());

        if (client.isEmpty()) {
            throw new RuntimeException("Client not found");
        }
        Client updatedClient = client.get();
        updateIfNotNull(requestClientDto.getFirstName(), updatedClient::setFirstName);
        updateIfNotNull(requestClientDto.getLastName(), updatedClient::setLastName);
        updateIfNotNull(requestClientDto.getEmail(), updatedClient::setEmail);
        clientRepository.save(updatedClient);

        return mapEntityToDto(updatedClient, uC -> new ResponseUpdatedClient(
                uC.getFirstName(),
                uC.getLastName(),
                uC.getEmail(),
                uC.getUpdatedAt())
        );
    }

    public void addEventToHistory(SubscriptionEventDto eventDto){
        EventHistory eventHistory = new EventHistory(
                eventDto.getClientId(),
                eventDto.getTitle(),
                eventDto.getService(),
                eventDto.getMessage());
        eventHistoryRepository.save(eventHistory);
    }

    public Client addAccountToNewClient(AccountCreatedDto accountCreated) {
       Optional<Client> client = clientRepository.findByClientId(accountCreated.getClientId());
       if (client.isEmpty()) {
           throw new RuntimeException("Client not found");
       }
       Client newClient = client.get();
       newClient.setAccountId(accountCreated.getAccountId());
       clientRepository.save(newClient);
       return newClient;
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
