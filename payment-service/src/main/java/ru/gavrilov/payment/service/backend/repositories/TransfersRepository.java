package ru.gavrilov.payment.service.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.payment.service.backend.entities.Transfer;

@Repository
public interface TransfersRepository extends JpaRepository<Transfer, Long> {

}

