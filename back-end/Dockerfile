FROM openjdk:8-alpine

COPY target/meme-share-1.0.0.jar /app/app.jar

WORKDIR /app

ENTRYPOINT [ "java", "-jar", "app.jar" ]


