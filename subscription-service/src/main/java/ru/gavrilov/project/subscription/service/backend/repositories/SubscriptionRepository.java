package ru.gavrilov.project.subscription.service.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.project.subscription.service.backend.entities.Subscription;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByClientId(Long clientId);

    Optional<Subscription> findByClientIdAndSubscriptionId(Long clientId, Long subscriptionId);
}
