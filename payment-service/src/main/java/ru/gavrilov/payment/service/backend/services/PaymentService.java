package ru.gavrilov.payment.service.backend.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gavrilov.payment.service.backend.dtos.AccountCreatedDto;
import ru.gavrilov.payment.service.backend.dtos.ClientCreatedDto;
import ru.gavrilov.payment.service.backend.dtos.PaymentActionRequestDto;
import ru.gavrilov.payment.service.backend.entities.Account;
import ru.gavrilov.payment.service.backend.entities.Transfer;
import ru.gavrilov.payment.service.backend.erorrs.AppLogicException;
import ru.gavrilov.payment.service.backend.kafka.KafkaProducer;
import ru.gavrilov.payment.service.backend.repositories.AccountsRepository;
import ru.gavrilov.payment.service.backend.repositories.TransfersRepository;

import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {
    private final AccountsRepository accountRepository;
    private final TransfersRepository transfersRepository;
    private final KafkaProducer kafkaProducer;

    private static <E, D> D mapEntityToDto(E entity, Function<E, D> mapper) {
        return mapper.apply(entity);
    }

    public void createAccount(ClientCreatedDto clientCreated) {
        Account account = new Account(1L, clientCreated.getClientId());
        accountRepository.save(account);
        AccountCreatedDto accountCreated = mapEntityToDto(account, a -> new AccountCreatedDto(account.getAccountId(), account.getClientId()));
        kafkaProducer.sendMessage(accountCreated);
        log.info("Account created: {}", accountCreated);
    }

    @Transactional
    public void executePayment(PaymentActionRequestDto paymentActionRequestDto) {
        Transfer transfer = new Transfer(
                paymentActionRequestDto.getClientId(),
                paymentActionRequestDto.getServiceName(),
                paymentActionRequestDto.getServicePrice()
                );
        transfersRepository.save(transfer);

        Optional<Account> isPresentAccount = accountRepository.findByClientId(paymentActionRequestDto.getClientId());
        Account account = isPresentAccount.orElseThrow(() -> new AppLogicException("NOT FOUND ERROR", "account not found client Id"));


    }
}
