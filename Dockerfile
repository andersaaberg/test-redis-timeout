FROM openjdk:14-alpine
COPY build/libs/test-redis-timeout-*-all.jar test-redis-timeout.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "test-redis-timeout.jar"]