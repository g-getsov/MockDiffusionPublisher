FROM openjdk:8-jre-alpine
ADD ./target/springBootMockDiffusionPublisher.jar /usr/mockpublisher.jar
RUN sleep 10 && java -jar /usr/mockpublisher.jar
EXPOSE 6786
