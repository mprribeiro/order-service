FROM openjdk:11
VOLUME /tmp
ADD ./target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["java","-jar","/order-service.jar"]
