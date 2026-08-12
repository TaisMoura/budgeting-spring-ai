# DIO Spring Boot - Final Project 05: Spring AI (budgeting)

## Introduction

This final module applies Spring AI in a budgeting API while preserving the same layered architecture used across the track.

The goal is to integrate AI capabilities without bypassing domain and use case boundaries.

## Code Context

The project processes voice commands to create and query financial transactions.

Primary flow:

1. Client uploads an audio file.
2. Audio is transcribed into text.
3. The model selects an application tool/use case.
4. The use case persists or queries transaction data.
5. The final response is converted to audio.

## Project Structure

- `src/main/java/dio/budgeting/domain`
  - Domain model and repository contract.
- `src/main/java/dio/budgeting/application`
  - Use cases used by both REST and AI tool calling.
- `src/main/java/dio/budgeting/infrastructure`
  - HTTP adapters, JPA adapters, and integration glue.

## Module-Specific Topics

### Speech-to-text

- Uses `TranscriptionModel` for audio transcription.
- Model settings are configured in `application.properties`.

### Tool calling

- `ChatClient` registers use-case tools.
- `@Tool` methods expose business capabilities to the model.

### Text-to-speech

- `TextToSpeechModel` produces MP3 output from final text.
- AI endpoint returns generated audio.

## Spring AI Documentation

- Spring AI Reference: https://docs.spring.io/spring-ai/reference/index.html
- ChatModel API: https://docs.spring.io/spring-ai/reference/api/chatmodel.html
- ChatClient API: https://docs.spring.io/spring-ai/reference/api/chatclient.html
- Tools API: https://docs.spring.io/spring-ai/reference/api/tools.html
- Audio Transcriptions API: https://docs.spring.io/spring-ai/reference/api/audio/transcriptions.html
- Audio Speech API: https://docs.spring.io/spring-ai/reference/api/audio/speech.html

## Shared Architecture References

Common architecture concepts are documented in the root README:

- [DDD layers](../README.md#ddd-layered-architecture)
- [Class vs record](../README.md#java-class-vs-java-record-in-domain-modeling)
- [Strong typed identifiers](../README.md#strong-typed-identifiers)
- [Repository pattern](../README.md#repository-pattern)
- [Use cases and Clean Architecture](../README.md#use-cases-and-clean-architecture)
- [Docker Compose support](../README.md#docker-compose-support-in-development)

## How to Run

Set your OpenAI API key:

```bash
export OPENAI_API_KEY="your_api_key_here"
```

Run the application and tests:

```bash
./gradlew bootRun
./gradlew test
```

## Notes

- Educational final project focused on AI plus architectural discipline.
- External provider integration tests may require active credentials.

## My Implementation — Financial Summary

As an improvement to the original project, I implemented a financial summary feature for the registered transactions.

The feature calculates:

- Total number of transactions
- Total transaction amount
- Average transaction amount
- Highest transaction amount
- Lowest transaction amount

### Financial Summary Use Case

The business rule was implemented in:

`src/main/java/dio/budgeting/application/GetFinancialSummaryUseCase.java`

The use case retrieves all transactions through the `TransactionRepository` and calculates the financial indicators.

The existing domain and application boundaries are preserved, keeping the business logic outside the HTTP controller.

### REST Endpoint

The feature is exposed through:

`GET /transactions/summary`

Example response:

```json
{
  "totalTransactions": 4,
  "totalAmount": 280.0,
  "averageAmount": 70.0,
  "highestAmount": 100.0,
  "lowestAmount": 30.0
}
```
## Portfolio Overview

This project demonstrates the integration of Artificial Intelligence capabilities into a Spring Boot financial application while preserving separation of responsibilities and application boundaries.

### My Contribution

As an improvement to the original project, I implemented a Financial Summary feature for registered transactions.

The feature provides:

- Total number of transactions
- Total transaction amount
- Average transaction amount
- Highest transaction amount
- Lowest transaction amount

The business rule is implemented in the application layer through the `GetFinancialSummaryUseCase`.

The same business capability can be consumed through the REST API and exposed to Spring AI through Tool Calling.

### Technologies

- Java
- Spring Boot
- Spring AI
- REST API
- Clean Architecture concepts
- Domain-Driven Design concepts
- Repository Pattern
- JUnit 5
- Mockito
- MockMvc
- Gradle
- Git and GitHub

### Quality and Validation

The project includes automated tests covering the financial summary use case and REST controller.

The complete test suite was validated with:

```bash
./gradlew clean test
```
Result:

```text
BUILD SUCCESSFUL
```

