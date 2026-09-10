# Monitoring Platform

Monitoring Platform is a microservice-based application for monitoring endpoint availability and processing status changes.

## Current features

* User registration and authentication
* JWT-based authorization
* API Gateway
* Request rate limiting
* Circuit Breaker for service fault tolerance
* Endpoint monitoring
* Monitor members and roles
* Incident processing
* Asynchronous communication between services via Apache Kafka
* PostgreSQL persistence
* Kafka UI for local development and debugging

## Services

* **API Gateway** — entry point for client requests
* **Auth Service** — registration, authentication and JWT generation
* **Monitor Service** — monitor and member management
* **Incident Service** — processes monitoring results and status changes
* **Notification Service** — handles notification-related events

## Tech Stack

* Java
* Spring Boot
* Spring Security
* Spring Cloud Gateway
* Apache Kafka
* PostgreSQL
* Docker
* Maven

## Status

🚧 The project is currently under active development.

More detailed documentation, architecture diagrams, API descriptions and setup instructions will be added later.
