FROM maven:3.8.6-eclipse-temurin-17-alpine AS backend-build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src src
RUN ["mvn", "package", "-Dmaven.test.skip=true"]
RUN mvn package -Dmaven.test.skip=true && mv target/*.jar target/app.jar
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../../target/app.jar)

FROM openjdk:17-jdk-alpine
EXPOSE 8080
ARG DEPENDENCY=/app/target/dependency
COPY --from=backend-build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=backend-build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=backend-build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.compass.reinan.api_ecommerce.ApiEcommerceApplication"]