FROM openjdk:11-bullseye AS build
ENV APPLICATION_VERSION="1.0-SNAPSHOT"
COPY . /project/
WORKDIR /project
RUN ./gradlew clean build --no-daemon

FROM openjdk:11-bullseye
ENV APPLICATION_VERSION="1.0-SNAPSHOT"
COPY --from=build /project/build/libs/song-microservice-${APPLICATION_VERSION}.jar /home/application/song-microservice-${APPLICATION_VERSION}.jar
EXPOSE 8080
ENTRYPOINT java -jar /home/application/song-microservice-${APPLICATION_VERSION}.jar