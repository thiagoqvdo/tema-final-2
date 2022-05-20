FROM openjdk:11-bullseye AS build
ENV APPLICATION_VERSION="1.0"
COPY . /project/
WORKDIR /project
RUN ./gradlew clean build --no-daemon

FROM openjdk:11-bullseye
ENV APPLICATION_VERSION="1.0"
COPY --from=build /project/build/libs/app-microservice-${APPLICATION_VERSION}.jar /home/application/app-microservice-${APPLICATION_VERSION}.jar
EXPOSE 8080
ENTRYPOINT java -jar /home/application/app-microservice-${APPLICATION_VERSION}.jar