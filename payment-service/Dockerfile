# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy AS deps
WORKDIR /build
COPY . .
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

FROM deps AS package
WORKDIR /build
COPY . .
RUN --mount=type=bind,source=pom.xml,target=pom.xml \
    --mount=type=cache,target=/root/.m2 \
    ./mvnw package -DskipTests && \
    mkdir -p target && \
    mv payment-service/target/payment-service-0.0.1-SNAPSHOT.jar target/app.jar

FROM package AS extract
WORKDIR /build
RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted

FROM eclipse-temurin:17-jre-jammy AS final
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser
WORKDIR /app
COPY --from=extract /build/target/extracted/dependencies/ ./
COPY --from=extract /build/target/extracted/spring-boot-loader/ ./
COPY --from=extract /build/target/extracted/snapshot-dependencies/ ./
COPY --from=extract /build/target/extracted/application/ ./
EXPOSE 8080
ENTRYPOINT [ "java", "org.springframework.boot.loader.launch.JarLauncher" ] 