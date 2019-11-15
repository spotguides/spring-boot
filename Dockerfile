FROM maven:3.6-jdk-11-slim as build

# Resolve all the dependencies and cache them to save a LOT of time
COPY pom.xml /usr/src/myapp/
RUN mvn -f /usr/src/myapp/pom.xml dependency:resolve dependency:resolve-plugins

# Build the application, usually only this part gets rebuilt locally, use offline mode and skip tests
COPY src /usr/src/myapp/src
RUN mvn -f /usr/src/myapp/pom.xml clean package -DskipTests -o

# The final image should have minimal layers
FROM openjdk:11-jre-slim
RUN apt-get update && apt-get install curl -y
COPY --from=build /usr/src/myapp/target/app.jar app.jar
ENTRYPOINT java -jar app.jar
