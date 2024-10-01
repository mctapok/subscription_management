package ru.gavrilov.project.subscription.service.backend.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gavrilov.project.subscription.service.backend.dtos.KafkaEventDto;
import ru.gavrilov.project.subscription.service.backend.dtos.RequestSubscriptionDto;
import ru.gavrilov.project.subscription.service.backend.dtos.ResponseSubscriptionDto;
import ru.gavrilov.project.subscription.service.backend.entities.Subscription;
import ru.gavrilov.project.subscription.service.backend.entities.SubscriptionPlan;
import ru.gavrilov.project.subscription.service.backend.kafka.KafkaProducer;
import ru.gavrilov.project.subscription.service.backend.repositories.SubscriptionPlanRepository;
import ru.gavrilov.project.subscription.service.backend.repositories.SubscriptionRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final SubscriptionPlanRepository plansRepository;
    private final KafkaProducer kafkaProducer;

    private static <E, D> D mapEntityToDto(E entity, Function<E, D> mapper) {
        return mapper.apply(entity);
    }

    public List<ResponseSubscriptionDto> getAllSubscriptions(Long clientId) {
        List<SubscriptionPlan> plans = plansRepository.findAll();
        List<Subscription> clientSubscriptions = repository.findAllByClientId(clientId);
        return returnSubscriptions(clientSubscriptions, plans);
    }

    public ResponseSubscriptionDto createNewSubscription(Long clientId, RequestSubscriptionDto requestSubscriptionDto) {
        SubscriptionPlan subscriptionPlan = plansRepository.findBySubscriptionTitle(requestSubscriptionDto.getSubscriptionTitle());
        Subscription newSubscription = new Subscription(clientId,
                subscriptionPlan.getId(),
                subscriptionPlan.getSubscriptionTitle(),
                subscriptionPlan.getPrice(),
                "Active");
        newSubscription = repository.save(newSubscription);

        KafkaEventDto eventDto = new KafkaEventDto(clientId, newSubscription.getSubscriptionTitle(), "new subscription", "subscription-service");
        kafkaProducer.sendMessage(eventDto);

        return mapEntityToDto(newSubscription, newS-> new ResponseSubscriptionDto(
                newS.getSubscriptionId(),
                newS.getSubscriptionTitle(),
                newS.getPrice(),
                newS.getStatus())
        );
    }

    public void unsubscribe(Long clientId, RequestSubscriptionDto requestSubscriptionDto) {
        Subscription unsubscribe = repository.findByClientIdAndSubscriptionId(clientId, requestSubscriptionDto.getSubscriptionId())
                .orElseThrow(() -> new RuntimeException("subscription not found"));
        repository.delete(unsubscribe);

    }

    private List<ResponseSubscriptionDto> returnSubscriptions(List<Subscription> subscriptions, List<SubscriptionPlan> plans) {
        Map<Long, String> statusIds = new HashMap<>();
        for (Subscription subscription : subscriptions) {
            statusIds.put(
                    subscription.getSubscriptionId(),
                    subscription.getStatus()
            );
        }
        return plans.stream().map(p -> new ResponseSubscriptionDto(
                        p.getId(),
                        p.getSubscriptionTitle(),
                        p.getPrice(),
                        statusIds.getOrDefault(p.getId(), "NOT_SUBSCRIBED")))
                .toList();
    }
}
