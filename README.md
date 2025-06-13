# BalHom Statistics API

BalHom Statistics API acts as a microservice for the BalHom infrastructure, providing functionalities to manage
statistics.

## Table of Contents

- [Environment Variables](#environment-variables)
- [Error Codes](#error-codes)
- [Service Development](#service-development)

## Environment Variables

| Name                                               | Description                                                                                                    |
|----------------------------------------------------|----------------------------------------------------------------------------------------------------------------|
| KEYCLOAK_URL                                       | Keycloak instance url. Ex: http://localhost:7080                                                               |
| KEYCLOAK_REALM                                     | Keycloak instance realm name. Default: balhom-realm                                                            |
| KEYCLOAK_CLIENT_ID                                 | Keycloak instance client id. Default: balhom-client                                                            |
| MONGODB_URL                                        | Mongo instance url                                                                                             |
| MONGODB_DB                                         | Mongo instance database name. Default: balHomStatisticsDB                                                      |
| KAFKA_SERVERS                                      | Kafka server urls                                                                                              |
| CURRENCY_PROFILES_API_URL                          | Currency Profiles API instance url. Ex: http://balhom-currency-profiles-api:8081                               |
| QUARKUS_HTTP_CORS_ORIGINS                          | CORS origins                                                                                                   |
| QUARKUS_HTTP_CORS_ORIGINS                          | CORS origins. Optional                                                                                         |
| QUARKUS_HTTP_CORS_HEADERS                          | Headers allowed. Optional                                                                                      |
| QUARKUS_HTTP_CORS_EXPOSED_HEADERS                  | Headers exposed in responses. Optional                                                                         |
| QUARKUS_HTTP_CORS_ACCESS_CONTROL_MAX_AGE           | Informs the browser how long it can cache the results of a preflight request. Optional                         |
| QUARKUS_HTTP_CORS_ACCESS_CONTROL_ALLOW_CREDENTIALS | Tells browsers if front-end can be allowed to access credentials when the request’s credentials mode. Optional |

## Error Codes

| Code | Description                            |
|------|----------------------------------------|
| 200  | "Currency profile reference not found" |

> **1 to 99** Generic errors \
> **100 to 199** Statistics related errors \
> **200 to 299** Currency Profile Changes related errors

## Service Development

This API is developed using [Quarkus](https://quarkus.io/), a Kubernetes-native Java stack tailored for GraalVM and
OpenJDK HotSpot, and [Kotlin](https://kotlinlang.org/), a modern programming language.

### Development Environment

- **IDE**: The project is developed using [IntelliJ IDEA](https://www.jetbrains.com/idea/).
- **Docker**: The infrastructure dependencies for development are managed using Docker. You can find the Docker Compose
  configuration in `infra/dev/docker-compose.yaml`.

### Running in Development Mode

To run the application in development mode with live coding enabled, use the `quarkus [dev]` profile in IntelliJ IDEA.
This can be done by using the stored run configuration.
