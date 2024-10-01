package ru.gavrilov.project.subscription.service.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.gavrilov.project.subscription.service.backend.dtos.RequestSubscriptionDto;
import ru.gavrilov.project.subscription.service.backend.dtos.ResponseSubscriptionDto;
import ru.gavrilov.project.subscription.service.backend.dtos.SubscriptionsListDto;
import ru.gavrilov.project.subscription.service.backend.services.SubscriptionService;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping
    public SubscriptionsListDto getAllSubscriptions(@RequestHeader Long clientId) {
        return new SubscriptionsListDto(subscriptionService.getAllSubscriptions(clientId));
    }

    @PostMapping("/{clientId}/new-subscription")
    public ResponseSubscriptionDto createNewSubscription(@PathVariable Long clientId,
                                                         @RequestBody RequestSubscriptionDto requestSubscriptionDto) {
        return subscriptionService.createNewSubscription(clientId,  requestSubscriptionDto);
    }

    @PostMapping("/{clientId}/unsubscribe")
    public void unsubscribe(@PathVariable Long clientId,
                                       @RequestBody RequestSubscriptionDto requestSubscriptionDto){
        subscriptionService.unsubscribe(clientId, requestSubscriptionDto);
    }

}
