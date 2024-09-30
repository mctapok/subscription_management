package ru.gavrilov.project.client.service.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_history")
public class EventHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "client_id")
    Long clientId;
    @Column(name = "title")
    String title;
    @Column(name = "service")
    String service;
    @Column(name = "message")
    String message;
    @Column(name = "created_at")
    @CreationTimestamp
    Timestamp createdAt;

    public EventHistory(Long clientId, String title, String service, String message) {
        this.clientId = clientId;
        this.title = title;
        this.service = service;
        this.message = message;
    }
}
