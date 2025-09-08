package ru.gavrilov.project.subscription.service.backend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gavrilov.project.subscription.service.backend.dtos.RequestSubscriptionDto;
import ru.gavrilov.project.subscription.service.backend.dtos.ResponseSubscriptionDto;
import ru.gavrilov.project.subscription.service.backend.entities.Subscription;
import ru.gavrilov.project.subscription.service.backend.entities.SubscriptionPlan;
import ru.gavrilov.project.subscription.service.backend.errors.RepeatSubscriptionException;
import ru.gavrilov.project.subscription.service.backend.errors.ResourceNotFoundException;
import ru.gavrilov.project.subscription.service.backend.kafka.KafkaProducer;
import ru.gavrilov.project.subscription.service.backend.repositories.SubscriptionPlanRepository;
import ru.gavrilov.project.subscription.service.backend.repositories.SubscriptionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SubscriptionServiceTest {
    @Mock
    private SubscriptionRepository repository;
    @Mock
    private SubscriptionPlanRepository plansRepository;
    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    SubscriptionService subscriptionService;

    @Test
    public void getAllSubscriptions_AllSubscriptions() {
        Long clientId = 1L;

        List<SubscriptionPlan> subscriptionsPlans = List.of(SubscriptionPlan.builder().id(1L).subscriptionTitle("netflix").price(new BigDecimal(14)).duration(30L).build(), SubscriptionPlan.builder().id(2L).subscriptionTitle("news").price(new BigDecimal(9)).duration(30L).build());

        List<Subscription> subscription = List.of(Subscription.builder().subscriptionId(2L).subscriptionTitle("news").clientId(clientId).status("ACTIVE").build());

        when(plansRepository.findAll()).thenReturn(subscriptionsPlans);
        when(repository.findAllByClientId(clientId)).thenReturn(subscription);

        List<ResponseSubscriptionDto> responseSubscriptions = subscriptionService.getAllSubscriptions(clientId);

        Assertions.assertEquals(2, responseSubscriptions.size());
        Assertions.assertEquals("NOT_SUBSCRIBED", responseSubscriptions.get(0).getStatus());
        Assertions.assertEquals("ACTIVE", responseSubscriptions.get(1).getStatus());
    }

    @Test
    public void createNewSubscription_ReturnNewSub() {
        SubscriptionPlan subscriptionPlan = SubscriptionPlan.builder().id(1L).price(new BigDecimal(11)).subscriptionTitle("netflix").build();
        Subscription newSubscription = Subscription.builder().price(new BigDecimal(11)).subscriptionId(1L).subscriptionTitle("netflix").clientId(1L).status("Active").build();

        RequestSubscriptionDto requestSubscriptionDto = new RequestSubscriptionDto(1L, 1L, "netflix", new BigDecimal(1), "Active");

        when(repository.findByClientIdAndSubscriptionTitle(1L, "netflix")).thenReturn(Optional.empty());
        when(plansRepository.findBySubscriptionTitle(requestSubscriptionDto.getSubscriptionTitle())).thenReturn(subscriptionPlan);
        when(repository.save(any(Subscription.class))).thenReturn(newSubscription);

        ResponseSubscriptionDto response = subscriptionService.createNewSubscription(1L, requestSubscriptionDto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals("netflix", response.getSubscriptionTitle());
        Assertions.assertEquals("Active", response.getStatus());
        verify(repository).save(newSubscription);
        verify(kafkaProducer).sendMessage(any());
    }

    @Test
    public void createNewSubscription_ReturnError() {
        RequestSubscriptionDto requestSubscriptionDto = new RequestSubscriptionDto(1L, 1L, "netflix", new BigDecimal(1), "ACTIVE");
        Subscription existingSubscription = Subscription.builder().subscriptionTitle("netflix").status("Active").build();

        when(repository.findByClientIdAndSubscriptionTitle(1L, requestSubscriptionDto.getSubscriptionTitle())).thenReturn(Optional.of(existingSubscription));

        RepeatSubscriptionException exception = Assertions.assertThrows(RepeatSubscriptionException.class,
                () -> subscriptionService.createNewSubscription(1L, requestSubscriptionDto)
        );
        Assertions.assertEquals("Подписка уже активна", exception.getMessage());
        Mockito.verify(repository, never()).save(any());
    }

    @Test
    public void unsubscribe_Successfully() {
        Subscription unsubscribe = Subscription.builder().subscriptionId(1L).clientId(1L).build();
        RequestSubscriptionDto requestSubscriptionDto = new RequestSubscriptionDto(1L, 1L, "netflix", new BigDecimal(1), "ACTIVE");

        when(repository.findByClientIdAndSubscriptionId(1L, 1L)).thenReturn(Optional.of(unsubscribe));

        subscriptionService.unsubscribe(1L, requestSubscriptionDto);
        Mockito.verify(repository).delete(any());
    }

    @Test
    public void unsubscribe_unSuccessfully() {
        RequestSubscriptionDto requestSubscriptionDto = new RequestSubscriptionDto(1L, 1L, "netflix", new BigDecimal(1), "ACTIVE");

        when(repository.findByClientIdAndSubscriptionId(1L, requestSubscriptionDto.getSubscriptionId())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class,
                () -> subscriptionService.unsubscribe(1L, requestSubscriptionDto)
        );

        Assertions.assertEquals("У Вас нет такой подписки", exception.getMessage());
        Mockito.verify(repository, never()).delete(any());
    }
}
