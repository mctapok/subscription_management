package ru.gavrilov.payment.service.backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gavrilov.payment.service.backend.dtos.AccountCreatedDto;
import ru.gavrilov.payment.service.backend.dtos.ClientCreatedDto;
import ru.gavrilov.payment.service.backend.entities.Account;
import ru.gavrilov.payment.service.backend.kafka.KafkaProducer;
import ru.gavrilov.payment.service.backend.repositories.AccountRepository;

import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final AccountRepository accountRepository;
    private final KafkaProducer kafkaProducer;

    private static <E, D> D mapEntityToDto(E entity, Function<E, D> mapper) {
        return mapper.apply(entity);
    }

    public void createAccount(ClientCreatedDto clientCreated) {
        Account account = new Account(1L, clientCreated.getClientId());
        accountRepository.save(account);
        AccountCreatedDto accountCreated =  mapEntityToDto(account, a -> new AccountCreatedDto(account.getAccountId(), account.getClientId()));
        kafkaProducer.sendMessage(accountCreated);
        log.info("Account created: {}", accountCreated);
    }
}
