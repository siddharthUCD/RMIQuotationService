FROM openjdk:8-jre-alpine
COPY target/core-0.0.1-jar-with-dependencies.jar/ core-0.0.1.jar
CMD ["/usr/bin/java", "-cp", "/core-0.0.1.jar", "service.Server"]
