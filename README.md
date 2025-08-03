# Subscription Management Microservices

Этот проект — система управления подписками, реализованная на Java (Spring Boot) с использованием микросервисной архитектуры. Включает три основных сервиса:

- **client-service** — управление клиентами, регистрация, хранение данных.
- **subscription-service** — управление подписками, создание, обновление, удаление, связь с клиентами и платежами.
- **payment-service** — обработка платежей, интеграция с внешними платёжными системами.

## Технологии
- Java 17 (Eclipse Temurin)
- Spring Boot
- Maven (wrapper)
- PostgreSQL
- Kafka (Wurstmeister)
- Docker, Docker Compose

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

