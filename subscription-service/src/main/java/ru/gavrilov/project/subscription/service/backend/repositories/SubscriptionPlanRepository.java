package ru.gavrilov.project.subscription.service.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import ru.gavrilov.project.subscription.service.backend.entities.SubscriptionPlan;

import java.util.List;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    @Override
    List<SubscriptionPlan> findAll();
    SubscriptionPlan findBySubscriptionTitle(String name);
}
