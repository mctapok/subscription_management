package ru.gavrilov.project.subscription.service.backend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import ru.gavrilov.project.subscription.service.backend.dtos.KafkaEventDto;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "history";
    private ObjectMapper mapper;

    public void sendMessage(KafkaEventDto eventDto) {
        try{
            String message = mapper.writeValueAsString(eventDto);
            kafkaTemplate.send(TOPIC, message);
            log.info("Message sent {}", message);
        }catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
