FROM amazoncorretto:8
RUN mkdir /opt/app
WORKDIR /opt/app
COPY target/*.jar .
ENTRYPOINT ["java","-jar","codingchallenge-0.0.1-SNAPSHOT.jar"]
