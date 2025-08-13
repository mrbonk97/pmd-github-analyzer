# ==============================
# Stage 1: Build fat jar
# ==============================
FROM gradle:8.14-jdk21 AS builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY src ./src

RUN gradle clean build --no-daemon

# ==============================
# Stage 2: Runtime
# ==============================
FROM openjdk:21-slim

RUN apt-get update && apt-get install -y git && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/build/libs/analyzer-1.0.jar analyzer-1.0.jar

ENTRYPOINT ["java", "-jar", "analyzer-1.0.jar"]