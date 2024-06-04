## Getting Started

### Clone the Repository

```bash
git clone 
```

## Local run automated tests

```bash
1 ./gradlew clean test allureReport
2 open build/reports/allure-report/index.html
```

## Run tests in Docker

```bash
1 docker-compose up --build
2 open http://localhost:8080
```
