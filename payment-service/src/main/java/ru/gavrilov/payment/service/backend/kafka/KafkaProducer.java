package ru.gavrilov.payment.service.backend.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gavrilov.payment.service.backend.dtos.AccountCreatedDto;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, AccountCreatedDto> kafkaTemplate;
    private static final String TOPIC = "account-created";

    public void sendMessage(AccountCreatedDto eventDto) {
        kafkaTemplate.send(TOPIC, eventDto);
        log.info("Message sent {}", eventDto);
    }
}
