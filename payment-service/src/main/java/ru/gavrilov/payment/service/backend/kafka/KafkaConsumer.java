package ru.gavrilov.payment.service.backend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.gavrilov.payment.service.backend.dtos.ClientCreatedDto;
import ru.gavrilov.payment.service.backend.dtos.PaymentActionRequestDto;
import ru.gavrilov.payment.service.backend.services.PaymentService;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final PaymentService paymentService;
    private ObjectMapper mapper;

    @KafkaListener(topics = "client-created", groupId = "payment-service-group")
    public void creationEvent(String message) {
        try {
            ClientCreatedDto clientCreated = mapper.readValue(message, ClientCreatedDto.class);
            paymentService.createAccount(clientCreated);
            log.info("Created account {}", clientCreated);
        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    @KafkaListener(topics = "payment_message", groupId = "payment-service-group2")
    public void subscriptionEvent(String message){
        try {
           PaymentActionRequestDto paymentActionRequestDto = mapper.readValue(message, PaymentActionRequestDto.class);
            paymentService.executePayment(paymentActionRequestDto);
            log.info("message {}, topic payment_message", paymentActionRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
