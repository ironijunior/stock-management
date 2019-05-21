FROM openjdk:8
COPY target/stock-management.jar stock-management.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "stock-management.jar"]