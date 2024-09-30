package ru.gavrilov.project.client.service.backend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.gavrilov.project.client.service.backend.dtos.AccountCreatedDto;
import ru.gavrilov.project.client.service.backend.dtos.SubscriptionEventDto;
import ru.gavrilov.project.client.service.backend.services.ClientService;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {
    private ClientService clientService;
    private ObjectMapper mapper;

    @KafkaListener(topics = "history", groupId = "subscription-service-group")
    public void subscriptionListener(String message) {
        try {
            SubscriptionEventDto eventDto = mapper.readValue(message, SubscriptionEventDto.class);
            clientService.addEventToHistory(eventDto);
            log.info(eventDto.toString());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "account-created", groupId = "payment-service-group")
    public void paymentListener(String message) {
        try {
            AccountCreatedDto accountCreated = mapper.readValue(message, AccountCreatedDto.class);
            clientService.addAccountToNewClient(accountCreated);
            log.info(accountCreated.toString());
        }catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
