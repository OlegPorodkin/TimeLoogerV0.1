FROM maven:3.9.9-eclipse-temurin-21 as builder

WORKDIR /app

COPY . /app/

RUN ls -l /app && ls -l /app/*

#RUN mvn clean install -N -f /app/pom.xml
RUN mvn clean install

RUN mvn dependency:resolve dependency:resolve-plugins

RUN mvn clean package spring-boot:repackage -pl time-logger-application -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/time-logger-application/target/*.jar app.jar

ENV JAVA_OPTS="-Xms512m -Xmx1024m"
#ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]