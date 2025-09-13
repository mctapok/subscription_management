package ru.gavrilov.payment.service.backend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.gavrilov.payment.service.backend.dtos.AccountCreatedDto;
import ru.gavrilov.payment.service.backend.dtos.PaymentActionRequestDto;
import ru.gavrilov.payment.service.backend.dtos.PaymentActionResponseDto;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, PaymentActionResponseDto> executePaymentKafkaTemplate;
    private static final String TOPIC = "account-created";
    private static final String TOPIC_B = "payment-action";
    private String logMessage = "Message sent {} topic {}";
    private ObjectMapper mapper;

    public void sendMessage(AccountCreatedDto eventDto) {
        try {
            String message = mapper.writeValueAsString(eventDto);
            kafkaTemplate.send(TOPIC, message);
            log.info(logMessage, eventDto, TOPIC);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }

    public void paymentExecuteMessage(PaymentActionResponseDto paymentActionResponseDto) {
        try {
            String message = mapper.writeValueAsString(paymentActionResponseDto);
            kafkaTemplate.send(TOPIC, message);
            log.info(logMessage, paymentActionResponseDto, TOPIC_B);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
