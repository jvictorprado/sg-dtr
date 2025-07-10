# Build Stage
FROM eclipse-temurin:21

RUN mkdir /app

COPY build/libs/sgdtr-backend.jar /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/sgdtr-backend.jar"]
# ENTRYPOINT ["/bin/sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app/sgdtr-backend.jar"]