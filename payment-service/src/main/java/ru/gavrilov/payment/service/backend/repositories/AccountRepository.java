package ru.gavrilov.payment.service.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gavrilov.payment.service.backend.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}

