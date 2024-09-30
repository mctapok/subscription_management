package ru.gavrilov.project.subscription.service.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionsListDto {
    private List<ResponseSubscriptionDto> subscriptions;
}
