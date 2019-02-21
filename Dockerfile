FROM maven:3.6.0-jdk-12-alpine AS build

COPY pom.xml /usr/src/app/pom.xml
RUN mvn -f /usr/src/app/pom.xml verify clean

COPY src /usr/src/app/src

RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java

COPY --from=build /usr/src/app/target/java-1.0-SNAPSHOT.jar /usr/app/java-1.0-SNAPSHOT.jar

COPY urls.txt /usr/app/urls.txt

WORKDIR /usr/app/

VOLUME /usr/app/output

ENTRYPOINT ["java","-jar","/usr/app/java-1.0-SNAPSHOT.jar"]
