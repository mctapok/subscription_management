package ru.gavrilov.project.client.service.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gavrilov.project.client.service.backend.dtos.RequestClientDto;
import ru.gavrilov.project.client.service.backend.dtos.ResponseClientDto;
import ru.gavrilov.project.client.service.backend.entities.Client;
import ru.gavrilov.project.client.service.backend.errors.AppLogicException;
import ru.gavrilov.project.client.service.backend.kafka.KafkaProducer;
import ru.gavrilov.project.client.service.backend.repositories.ClientRepository;
import ru.gavrilov.project.client.service.backend.repositories.EventHistoryRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {
    @Mock
    ClientRepository clientRepository;
    @Mock
    EventHistoryRepository eventHistoryRepository;
    @Mock
    KafkaProducer kafkaProducer;
    @InjectMocks
    ClientService clientService;
    @Test
    public void createNewClient_ReturnNewClientDto(){
        Client newClient = new Client(1L, "luke", "Skywalker", "tatuin@rpblc");
        RequestClientDto requestClientDto = new RequestClientDto(1L, "luke", "Skywalker", "tatuin@rpblc" );

        when(clientRepository.save(newClient)).thenReturn(newClient);

        ResponseClientDto response = clientService.createNewClient(requestClientDto);
        Assertions.assertEquals("luke", response.getFirstName());
        verify(kafkaProducer).sendClientCreated(any());
    }
    @Test
    public void createNewClient_ReturnException(){
        AppLogicException exception = Assertions.assertThrows(AppLogicException.class,
                () -> clientService.createNewClient(null));
        Assertions.assertEquals("VALIDATION_ERROR", exception.getCode());
    }
}
