package ru.gavrilov.project.subscription.service.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    @Column(name = "client_Id")
    private Long clientId;

    @Column(name = "subscription_title")
    private String subscriptionTitle;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "status")
    private String  status;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Subscription(Long clientId, Long subscriptionId, String subscriptionTitle,BigDecimal price, String status ) {
        this.clientId =clientId;
        this.subscriptionId = subscriptionId;
        this.subscriptionTitle = subscriptionTitle;
        this.price = price;
        this.status = status;
    }

}
