# Distributed Payment Processing Platform

A distributed payment processing system built using **Spring Boot microservices, Apache Kafka, and PostgreSQL**. This project demonstrates real-world backend concepts including **event-driven architecture, asynchronous processing, idempotency, retry handling, and application monitoring**.

## Architecture

```
Client
  |
  v
Payment Service (REST API)
  |
  | PaymentCreatedEvent
  v
Apache Kafka
  |
  v
Transaction Processor
  |
  | PaymentProcessedEvent
  v
Payment Service
```

## Features

* Built independent **Payment Service** and **Transaction Processor** microservices
* Implemented asynchronous communication using **Apache Kafka**
* Designed a complete payment lifecycle with `CREATED`, `PROCESSING`, `SUCCESS`, and `FAILED` states
* Added **Idempotency-Key** support to prevent duplicate payment creation
* Implemented retry handling for transient failures and maintained payment transaction history
* Integrated **PostgreSQL** using Spring Data JPA for persistent storage
* Added observability using **Spring Boot Actuator, Micrometer, and Prometheus**

## Technology Stack

* Java 17
* Spring Boot 3
* REST APIs
* Apache Kafka
* PostgreSQL
* Spring Data JPA / Hibernate
* Maven
* Docker
* Micrometer & Prometheus

## Running the Project

### Prerequisites

* Java 17
* Maven
* Docker Desktop
* PostgreSQL
* Apache Kafka

### Start the services

**Payment Service**

```bash
mvn spring-boot:run
```

**Transaction Processor**

```bash
mvn spring-boot:run
```

## Monitoring

Metrics are exposed through Spring Boot Actuator and scraped by Prometheus:

```
http://localhost:8080/actuator/prometheus
http://localhost:8081/actuator/prometheus
```

Key metrics include:

* Payments created, successful, and failed
* Retry counts
* HTTP request metrics
* JVM health metrics

## Future Improvements

* Grafana dashboards
* OpenTelemetry distributed tracing
* Docker Compose setup
* Kubernetes deployment
* CI/CD pipeline

## Key Engineering Concepts

Microservices • Event-Driven Architecture • Distributed Systems • Fault Tolerance • Idempotency • Retry Patterns • Observability
