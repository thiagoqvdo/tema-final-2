FROM openjdk:11-bullseye AS build
ENV SERVICE_VERSION="1.0"
COPY . /project/
WORKDIR /project
RUN ./gradlew clean build --no-daemon

FROM openjdk:11-bullseye
ENV SERVICE_VERSION="1.0"
COPY --from=build /project/build/libs/playlist-microservice-${APPLICATION_VERSION}.jar /home/application/playlist-microservice-${APPLICATION_VERSION}.jar
EXPOSE 8080
ENTRYPOINT java -jar /home/application/playlist-microservice-${APPLICATION_VERSION}.jar