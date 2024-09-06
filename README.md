# multitenancy

Api com Spring Boot 3 e <i>Multitenancy by Database</i> com bancos relacionais.

## Setup

No root do projeto subir containers:
```
docker-compose up
```

## Flyway

Configurada Flyway por Tenant.

### Curls
```
curl --location 'localhost:8080/v1/persons' \
--header 'X-Tenant-ID: 1'

curl --location 'localhost:8080/v1/persons' \
--header 'X-Tenant-ID: 2'
```