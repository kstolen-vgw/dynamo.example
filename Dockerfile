FROM azul/zulu-openjdk:20.0.2-20.32.11
EXPOSE 8080:8080

RUN mkdir /app

COPY ./build/libs/dynamo.example-0.0.1.jar ./app/application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
