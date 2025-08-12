package ru.gavrilov.payment.service.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "source_account_id")
    Long sourceAccountId;
    @Column(name = "service_id")
    Long serviceId;
    @Column(name = "service_name")
    String serviceName;
    @Column(name = "price")
    BigDecimal price;
    @Column(name = "isSuccess")
    boolean isSuccess;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Transfer(Long clientId, String serviceName, BigDecimal servicePrice){
        this.sourceAccountId = clientId;
        this.serviceName = serviceName;
        this.price = servicePrice;
    }
}
