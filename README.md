# Weather Tracker

Веб-приложение для отслеживания погоды в выбранных локациях.

## Описание

Пользователь регистрируется, ищет города, добавляет их в список и видит текущую погоду для каждой локации. Авторизация реализована через сессии и cookies.

## Стек технологий

- Java 21
- Spring MVC
- Hibernate
- PostgreSQL
- Flyway
- Thymeleaf
- Bootstrap 5
- OpenWeatherMap API
- Docker, Docker Compose

## Запуск через Docker

1. Клонируй репозиторий:
```bash
git clone https://github.com/aneG200229/Weather.git
cd Weather
```

2. Создай `.env` файл на основе `.env.example`:
```bash
cp .env.example .env
```

3. Заполни `.env` своими значениями:
```
DB_URL=jdbc:postgresql://db:5432/weather
DB_USERNAME=твой_username
DB_PASSWORD=твой_password
WEATHER_API_KEY=твой_ключ_от_openweathermap
```

4. Создай `src/main/resources/application.properties` на основе `application-git.properties` и заполни значениями.

5. Собери WAR и запусти:
```bash
./gradlew build
docker-compose up --build -d
```

6. Открой в браузере: `http://localhost:8080`

## Получение API ключа

Зарегистрируйся на [openweathermap.org](https://openweathermap.org) и получи бесплатный API ключ.

## О проекте

Проект выполнен в рамках учебного роадмапа по Java Backend разработке от [zhukovsd](https://zhukovsd.github.io/java-backend-learning-course/).

Ссылка на задание: [Weather Viewer](https://zhukovsd.github.io/java-backend-learning-course/projects/weather-viewer/)
