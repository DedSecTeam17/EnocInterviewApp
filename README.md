# Project Documentation: Clean Architecture with Jetpack Compose

## Table of Contents

1. [Project Overview](#project-overview)
2. [Architecture Overview](#architecture-overview)
3. [Features](#features)
    - [Authentication Feature](#authentication-feature)
    - [Home Feature](#home-feature)
4. [Testing](#testing)
    - [Unit Testing](#unit-testing)
    - [UI Testing](#ui-testing)
5. [CI/CD Pipeline](#ci-cd-pipeline)
6. [Test Coverage](#test-coverage)
7. [API Mocking](#api-mocking)
8. [Diagrams and Charts](#diagrams-and-charts)

## Project Overview

This project is built using **Clean Architecture** principles, with a focus on separation of concerns and scalability. The user interface is developed using **Jetpack Compose**, Android's modern toolkit for building native UI. The project is fully equipped with unit and UI testing, ensuring robustness and reliability. We have also integrated a CI/CD pipeline using **Fastlane** and **GitHub Actions** to automate the build, test, and deployment processes, as well as to maintain test coverage.

## Architecture Overview

### Clean Architecture Principles

The project is structured according to **Clean Architecture** principles, ensuring that the core business logic is independent of frameworks and tools. The architecture is divided into several layers:

- **Domain Layer**: Contains the business logic and use cases. It is completely independent of any other layer.
- **Data Layer**: Responsible for handling data operations. It interacts with the domain layer through repositories and provides data from local databases, remote APIs, or other sources.
- **Presentation Layer**: Contains ViewModels that prepare data for the UI. It communicates with the domain layer and transforms the data into a UI-friendly format.
- **UI Layer**: Built with **Jetpack Compose**, this layer is responsible for rendering the user interface. It interacts with the Presentation layer via ViewModels.

## Features

### Authentication Feature

The authentication feature handles user login, registration, and session management. It is designed to be secure, efficient, and user-friendly.

#### Authentication Flow Diagram

```plaintext
+----------------+        +--------------------+        +----------------------+
|  Login Screen  |  ----> |  AuthViewModel     |  ----> |  AuthUseCase          |
+----------------+        +--------------------+        +----------------------+
                                                      |
                                                      v
                                                +-------------------+
                                                |  AuthRepository   |
                                                +-------------------+
                                                      |
                                                      v
                                                +-------------------+
                                                |  AuthApiService   |
                                                +-------------------+
```

## Components

- **Login Screen**: Developed using Jetpack Compose. It interacts with the `AuthViewModel` to handle user input and displays authentication states.
- **AuthViewModel**: Manages the state of the authentication flow. It communicates with the `AuthUseCase` in the Domain layer.
- **AuthUseCase**: Contains business logic for authentication, interacting with the `AuthRepository`.
- **AuthRepository**: Provides authentication data from the network via `AuthApiService`.

## Testing

### Unit Testing

- All layers (ViewModel, UseCase, Repository) have been tested using JUnit and Mockito.

### UI Testing

- The login screen is tested using Espresso and Jetpack Compose UI Testing to ensure all states and transitions are correct.

## Home Feature

The home feature displays user information and a list of items fetched from a remote server. It uses Jetpack Compose to render the UI.

### Home Feature Flow Diagram

```plaintext
+----------------+        +---------------------+        +---------------------+
|  Home Screen   |  ----> |  HomeViewModel      |  ----> |  GetUserDataUseCase  |
+----------------+        +---------------------+        +---------------------+
                                                      |
                                                      v
                                                +--------------------+
                                                |  UserRepository    |
                                                +--------------------+
                                                      |
                                                      v
                                                +--------------------+
                                                |  UserApiService    |
                                                +--------------------+
```

## Components

- **Home Screen**: Developed using Jetpack Compose. Displays user data and a list of items.
- **HomeViewModel**: Manages the state of the home screen. It interacts with the `GetUserDataUseCase` and `GetItemsUseCase`.
- **GetUserDataUseCase & GetItemsUseCase**: These use cases contain the business logic for fetching user data and items.
- **UserRepository & ItemsRepository**: Provide data from network sources via respective API services.

## Testing

### Unit Testing

- Focuses on testing ViewModels and UseCases to ensure data is correctly processed and provided to the UI.

### UI Testing

- Home screen is tested with Jetpack Compose UI Testing to validate the display of data and user interactions.

## CI/CD Pipeline

We have integrated Fastlane and GitHub Actions to automate the entire process of building, testing, and deploying the application.

### Pipeline Steps

- **Build**: The application is built using Gradle.
- **Test**: Both unit tests and UI tests are executed.
- **Generate Coverage**: Coverage reports are generated and integrated into the CI pipeline.
- **Publish Coverage**: The coverage badge is updated in the README.
- **Deploy**: The application can be deployed or a new build artifact can be generated.

### Fastlane Configuration

Fastlane handles the following tasks:

- Building the project.
- Running tests and generating coverage reports.
- Pushing updates to GitHub.

## Test Coverage

Test coverage is enabled for both unit and UI tests. The coverage is continuously monitored and a badge displaying the latest coverage percentage is automatically updated in the README file.

### Test Coverage Badge

![Coverage](https://img.shields.io/badge/Coverage-85%25-brightgreen)

Coverage reports are generated and integrated into the CI/CD pipeline, ensuring that the project maintains high test coverage over time.

## API Mocking

Throughout the project, API mocking is used extensively during testing to simulate network responses and ensure consistent, repeatable tests. This is done with the help of **Retrofit**:

- **Retrofit**: A type-safe HTTP client for Android and Java. Retrofit is used in combination with mock implementations to simulate API responses during testing.
- **BehaviorDelegate**: Retrofit’s `BehaviorDelegate` is leveraged to create mock API responses, ensuring that tests are isolated from real network interactions and focus purely on the logic within the app.

This approach enables effective testing of network-related features without relying on external API availability, enhancing the reliability of the test suite.

## Diagrams and Charts

### Authentication Feature Flow

```plaintext
+----------------+        +--------------------+        +----------------------+
|  Login Screen  |  ----> |  AuthViewModel     |  ----> |  AuthUseCase          |
+----------------+        +--------------------+        +----------------------+
                                                      |
                                                      v
                                                +-------------------+
                                                |  AuthRepository   |
                                                +-------------------+
                                                      |
                                                      v
                                                +-------------------+
                                                |  AuthApiService   |
                                                +-------------------+
```


## Home Feature Flow

```plaintext
+----------------+        +---------------------+        +---------------------+
|  Home Screen   |  ----> |  HomeViewModel      |  ----> |  GetUserDataUseCase  |
+----------------+        +---------------------+        +---------------------+
                                                      |
                                                      v
                                                +--------------------+
                                                |  UserRepository    |
                                                +--------------------+
                                                      |
                                                      v
                                                +--------------------+
                                                |  UserApiService    |
                                                +--------------------+
```

## Conclusion

This project is a robust, scalable application built with modern Android development practices. By adhering to Clean Architecture and leveraging Jetpack Compose for the UI, the project is both maintainable and easy to extend. The CI/CD pipeline, coupled with thorough testing and API mocking, ensures that the application remains stable, well-covered, and reliable as new features are added.

If you have any questions or need further details, feel free to explore the code or reach out to me(mohammedelamin21.me@gmail.com).

