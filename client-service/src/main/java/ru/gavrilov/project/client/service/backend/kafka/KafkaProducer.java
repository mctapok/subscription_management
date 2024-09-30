package ru.gavrilov.project.client.service.backend.kafka;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gavrilov.project.client.service.backend.dtos.ClientCreatedDto;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private static final String TOPIC = "client-created";
    private final KafkaTemplate<String, ClientCreatedDto> kafkaTemplate;

    public void sendClientCreated(ClientCreatedDto clientCreated){
        kafkaTemplate.send(TOPIC, clientCreated);
        log.info("client ID {} sent", clientCreated.toString());
    }
}
