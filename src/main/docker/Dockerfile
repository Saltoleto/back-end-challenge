FROM openjdk:8-jdk-alpine as build
MAINTAINER Fernando Saltoleto Fidelis dos Santos
RUN apk update && apk upgrade
ENV PORT=9090
ADD target/*.jar app.jar
ENTRYPOINT java -Dserver.port=$PORT -jar app.jar