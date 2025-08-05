package ru.gavrilov.project.subscription.service.backend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import ru.gavrilov.project.subscription.service.backend.dtos.KafkaEventDto;
import ru.gavrilov.project.subscription.service.backend.dtos.RequestSubscriptionDto;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String TOPIC = "history";
    private static final String TOPIC_B = "payment_message";
    private ObjectMapper mapper;

    public void sendMessage(KafkaEventDto eventDto) {
        try{
            String message = mapper.writeValueAsString(eventDto);
            kafkaTemplate.send(TOPIC, message);
            log.info("Message sent {} topic {}", message, TOPIC);
        }catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
    public void requestPaymentAction(RequestSubscriptionDto requestSubscriptionDto){
        try{
            String message = mapper.writeValueAsString(requestSubscriptionDto);
            kafkaTemplate.send(TOPIC_B, message);
            log.info("Message sent {} topic {}", message, TOPIC_B);
        }catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}