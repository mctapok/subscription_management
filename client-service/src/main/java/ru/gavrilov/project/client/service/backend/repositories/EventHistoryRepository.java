package ru.gavrilov.project.client.service.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.project.client.service.backend.entities.EventHistory;

@Repository
public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {
}
