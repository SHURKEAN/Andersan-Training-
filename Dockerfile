# ---------- build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# copy Maven descriptor first so Docker cache works
COPY pom.xml .
COPY src/ ./src/

RUN mvn -q clean package -DskipTests

# ---------- runtime stage ----------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
# copy the fat-jar produced by Maven
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
