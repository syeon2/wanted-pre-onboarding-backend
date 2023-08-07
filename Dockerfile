FROM openjdk:11
RUN mkdir /app
ADD build/libs/*.jar /app
CMD [ "java", "-jar", "/app/assignment-0.0.1-SNAPSHOT.jar" ]
