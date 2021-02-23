FROM openjdk:11

ENV HOME /usr/src/app
WORKDIR $HOME
COPY bootJar.jar .

ENV SS_PORT 8080
EXPOSE $SS_PORT

ENTRYPOINT ["java", "-jar", "bootJar.jar"]