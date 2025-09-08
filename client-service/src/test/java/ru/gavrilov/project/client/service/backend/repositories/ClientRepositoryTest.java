package ru.gavrilov.project.client.service.backend.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import ru.gavrilov.project.client.service.backend.entities.Client;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private Long clientId = 2L;

    @Test
    public void createNewClient() {
        Client newClient = new Client(1L, "luke", "Skywalker", "tatuin@rpblc");
        Client savedClient = clientRepository.save(newClient);

        Assertions.assertNotNull(savedClient);
        Assertions.assertEquals("tatuin@rpblc", savedClient.getEmail());
    }

    @Test
    public void findByClientId_ReturnClient() {
        List<Client> clients = List.of(
                new Client(1L, "luke", "Skywalker", "tatuin@rpblc"),
                new Client(2L, "vader", "Skywalker", "deathstar@empr")
        );
        for (Client c : clients) {
            clientRepository.save(c);
        }
        Optional<Client> foundedClientById = clientRepository.findByClientId(clientId);

        Assertions.assertNotNull(foundedClientById);
        Assertions.assertEquals("vader", foundedClientById.get().getFirstName());
    }
}
