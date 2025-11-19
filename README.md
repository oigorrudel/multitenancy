# multitenancy

Aplicação de exemplo com **Spring Boot 3** que demonstra a implementação de **multitenancy por database** usando bancos relacionais.

---

## Visão Geral

Este projeto tem como objetivo demonstrar como habilitar multitenancy em uma aplicação Spring Boot, onde cada tenant possui seu próprio esquema/banco de dados físico. É uma abordagem útil quando você precisa isolar dados entre diferentes clientes de forma segura e eficiente.

---

## Tecnologias usadas

- Spring Boot 3  
- Spring Data JPA  
- HikariCP para pool de conexões  
- Flyway para migrações de banco por tenant  
- Docker / Docker Compose para orquestração de bancos de dados

---

## Setup

No diretório raiz do projeto, execute os containers com Docker Compose:

```bash
docker-compose up
```

## Como testar

```
curl --location 'http://localhost:8080/v1/persons' \
  --header 'X-Tenant-ID: 1'

curl --location 'http://localhost:8080/v1/persons' \
  --header 'X-Tenant-ID: 2'
```
