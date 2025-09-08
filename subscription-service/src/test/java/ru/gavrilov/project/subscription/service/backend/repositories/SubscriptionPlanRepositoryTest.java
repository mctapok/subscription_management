package ru.gavrilov.project.subscription.service.backend.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gavrilov.project.subscription.service.backend.entities.SubscriptionPlan;

import java.util.Arrays;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class SubscriptionPlanRepositoryTest {
    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Test
    public void findBySubscriptionTitle_ReturnsSubscription() {
        SubscriptionPlan subscriptionPlan = SubscriptionPlan.builder().subscriptionTitle("netflix").build();
        subscriptionPlanRepository.save(subscriptionPlan);

        SubscriptionPlan foundedSubscription = subscriptionPlanRepository.findBySubscriptionTitle("netflix");

        Assertions.assertNotNull(foundedSubscription);
        Assertions.assertEquals("netflix", foundedSubscription.getSubscriptionTitle());
    }

    @Test
    public void whenPlanDoesNotExist_ReturnNull() {
        SubscriptionPlan notFoundedPlan = subscriptionPlanRepository.findBySubscriptionTitle("netflix");
        Assertions.assertNull(notFoundedPlan);
    }

    @Test
    public void findAll_ReturnPlans() {
        List<SubscriptionPlan> plans = Arrays.asList(SubscriptionPlan.builder().subscriptionTitle("netflix").build(),
                SubscriptionPlan.builder().subscriptionTitle("news").build(),
                SubscriptionPlan.builder().subscriptionTitle("job").build()
        );

        for (SubscriptionPlan p : plans) {
            subscriptionPlanRepository.save(p);
        }

        List<SubscriptionPlan> foundedAllPlans = subscriptionPlanRepository.findAll();

        Assertions.assertNotNull(foundedAllPlans);
        Assertions.assertEquals(3, foundedAllPlans.size());
        Assertions.assertAll(
                () -> foundedAllPlans.stream().anyMatch(plan -> "netflix".matches(plan.getSubscriptionTitle())),
                () -> foundedAllPlans.stream().anyMatch(plan -> "news".matches(plan.getSubscriptionTitle())),
                () -> foundedAllPlans.stream().anyMatch(plan -> "job".matches(plan.getSubscriptionTitle()))
        );
    }
}
