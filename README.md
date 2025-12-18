# Subscription Management Microservices

Система управления подписками: backend-сервисы для клиентских аккаунтов, подписок и обработки платежей.  
Реализована микросервисная архитектура с event-driven взаимодействием через Kafka.  

## Обзор

Этот проект демонстрирует backend-архитектуру для управления подписками:
- регистрация клиентов
- создание/обновление/удаление подписок
- обработка событий платежей
- асинхронное взаимодействие между сервисами через Kafka

Система ориентирована на моделирование реального backend-корпорта.

Включает три основных сервиса:
- **client-service** — управление клиентами, регистрация, хранение данных.
- **subscription-service** — управление подписками, создание, обновление, удаление, связь с клиентами и платежами.
- **payment-service** — обработка платежей, интеграция с внешними платёжными системами.

## Architecture

```mermaid
flowchart LR
    ClientService[client-service]
    SubscriptionService[subscription-service]
    PaymentService[payment-service]
    Kafka[(Kafka)]
    DB1[(PostgreSQL)]
    DB2[(PostgreSQL)]
    DB3[(PostgreSQL)]

    ClientService --> DB1
    SubscriptionService --> DB2
    PaymentService --> DB3

    ClientService -->|account-created| Kafka
    Kafka -->|subscription-events| SubscriptionService
    Kafka -->|payment-events| PaymentService
    PaymentService -->|payment-result| Kafka


## Технологии
- Java 17, Spring Boot
- PostgreSQL (Spring Data JPA)
- Apache Kafka (event-driven communication)
- Docker, Docker Compose
- Maven

## Быстрый старт
1. Склонируйте репозиторий.
2. Запустите `docker-compose up --build` из корня проекта.
3. Все сервисы будут доступны на портах, указанных в `docker-compose.yml`.

## Структура репозитория
- `client-service/` — сервис клиентов
- `subscription-service/` — сервис подписок
- `payment-service/` — сервис платежей
- `docker-compose.yml` — оркестрация всех сервисов и инфраструктуры

## Для разработчиков
- Каждый сервис — отдельный Spring Boot проект
- Общий parent pom.xml для управления зависимостями
- Используется Maven Wrapper (`mvnw`)
- Для локальной разработки можно запускать сервисы отдельно через IDE или Maven

## Контакты
Автор: mctapok

