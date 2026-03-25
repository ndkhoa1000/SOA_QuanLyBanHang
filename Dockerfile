ARG MAVEN_IMAGE=maven:3.9.9-eclipse-temurin-8
ARG TOMCAT_IMAGE=tomcat:9.0.89-jdk8-temurin

FROM ${MAVEN_IMAGE} AS builder

ARG SERVICE_PROFILE=hoadon
ARG WAR_NAME=hoadon-service

WORKDIR /workspace
COPY pom.xml ./
COPY src ./src
COPY WebContent ./WebContent

RUN mvn -B -DskipTests clean package -P${SERVICE_PROFILE}

FROM ${TOMCAT_IMAGE}

ARG WAR_NAME=hoadon-service

RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=builder /workspace/target/${WAR_NAME}.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]