package ru.gavrilov.project.subscription.service.backend.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gavrilov.project.subscription.service.backend.entities.Subscription;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
public class SubscriptionRepositoryTest {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @BeforeEach
    public void initDb() {
        List<Subscription> subscriptionList = List.of(
                Subscription.builder().subscriptionId(1L).clientId(1L).subscriptionTitle("netflix").build(),
                Subscription.builder().subscriptionId(2L).clientId(1L).subscriptionTitle("news").build()
        );
        for (Subscription s : subscriptionList) {
            subscriptionRepository.save(s);
        }
    }


    @Test
    public void findAllByClientId_ReturnAllSubs() {
        List<Subscription> foundedAllSubscription = subscriptionRepository.findAllByClientId(1L);

        Assertions.assertNotNull(foundedAllSubscription);
        Assertions.assertEquals(2, foundedAllSubscription.size());
        Assertions.assertAll(
                () -> foundedAllSubscription.stream().anyMatch(s -> "netflix".matches(s.getSubscriptionTitle())),
                () -> foundedAllSubscription.stream().anyMatch(s -> "news".matches(s.getSubscriptionTitle()))
        );
    }

    @Test
    public void findByClientIdAndSubscriptionId_ReturnSubscription() {
        Optional<Subscription> subscription = subscriptionRepository.findByClientIdAndSubscriptionId(1L, 1L);

        Assertions.assertNotNull(subscription);
        Assertions.assertEquals(1L, subscription.get().getSubscriptionId(), "subs not found");
    }

    @Test
    public void findByClientIdAndSubscriptionTitle_ReturnSubscription() {
        Optional<Subscription> subscription = subscriptionRepository.findByClientIdAndSubscriptionTitle(1L, "netflix");

        Assertions.assertNotNull(subscription);
        Assertions.assertEquals("netflix", subscription.get().getSubscriptionTitle(), "subs not found");
    }

}
