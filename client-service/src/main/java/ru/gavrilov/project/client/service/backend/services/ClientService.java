package ru.gavrilov.project.client.service.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gavrilov.project.client.service.backend.dtos.*;
import ru.gavrilov.project.client.service.backend.entities.Client;
import ru.gavrilov.project.client.service.backend.entities.EventHistory;
import ru.gavrilov.project.client.service.backend.errors.AppLogicException;
import ru.gavrilov.project.client.service.backend.kafka.KafkaProducer;
import ru.gavrilov.project.client.service.backend.repositories.ClientRepository;
import ru.gavrilov.project.client.service.backend.repositories.EventHistoryRepository;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    private final EventHistoryRepository eventHistoryRepository;
    private final KafkaProducer kafkaProducer;
    private static final NotFoundError notFoundError = new NotFoundError();

    private static <E, D> D mapEntityToDto(E entity, Function<E, D> mapper) {
        return mapper.apply(entity);
    }

    public ResponseClientDto createNewClient(RequestClientDto requestClientDto) {
        if (requestClientDto != null) {
            Client newClient = new Client(requestClientDto.getClientId(), requestClientDto.getFirstName(), requestClientDto.getLastName(), requestClientDto.getEmail());
            kafkaProducer.sendClientCreated(new ClientCreatedDto(newClient.getClientId()));
            newClient = clientRepository.save(newClient);
            log.info("New client created: {}, {}", newClient.getClientId(), newClient.getEmail());
            return mapEntityToDto(newClient, nC -> new ResponseClientDto(nC.getFirstName()));
        } else {
            throw new AppLogicException("VALIDATION_ERROR", "Dto error");
        }
    }

    public ClientDetailsDto getDetails(Long clientId) {
        Client client = clientRepository.findByClientId(clientId).orElseThrow(() -> new AppLogicException(notFoundError.code, notFoundError.message));
        log.info("get details client by id {} :  {}", clientId, client.getEmail());
        return mapEntityToDto(client, c -> new ClientDetailsDto(
                c.getClientId(),
                c.getFirstName(),
                c.getLastName(),
                c.getEmail(),
                c.getCreatedAt())
        );
    }

    public ResponseUpdatedClient update(RequestClientDto requestClientDto) {
        Client client = clientRepository.findByClientId(requestClientDto.getClientId()).orElseThrow(() -> new AppLogicException(notFoundError.code, notFoundError.message));
        log.info("update client with id {} :  {}", requestClientDto.getClientId(), client.getEmail());
        updateIfNotNull(requestClientDto.getFirstName(), client::setFirstName);
        updateIfNotNull(requestClientDto.getLastName(), client::setLastName);
        updateIfNotNull(requestClientDto.getEmail(), client::setEmail);
        Client updatedClient = clientRepository.save(client);

        return mapEntityToDto(updatedClient, uC -> new ResponseUpdatedClient(
                uC.getFirstName(),
                uC.getLastName(),
                uC.getEmail(),
                uC.getUpdatedAt())
        );
    }

    public void addEventToHistory(SubscriptionEventDto eventDto) {
        EventHistory eventHistory = new EventHistory(
                eventDto.getClientId(),
                eventDto.getTitle(),
                eventDto.getService(),
                eventDto.getMessage()
        );
        eventHistoryRepository.save(eventHistory);
        log.info("new event created: {}, {}", eventHistory.getClientId(), eventHistory.getMessage());
    }

    public Client addAccountToNewClient(AccountCreatedDto accountCreated) {
        Client client = clientRepository.findByClientId(accountCreated.getClientId()).orElseThrow(() -> new AppLogicException(notFoundError.code, notFoundError.message));
        client.setAccountId(accountCreated.getAccountId());
        log.info("client with id: {}, account was set with id: {}", client.getClientId(), accountCreated.getAccountId());
        return clientRepository.save(client);
    }

    private <T> void updateIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    public static class NotFoundError {
        String code = "VALIDATION_ERROR";
        String message = "Client not found";
    }
}
