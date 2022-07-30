FROM adoptopenjdk/openjdk11:latest
RUN mkdir /opt/e-wallet
COPY target/e-wallet-0.0.1-SNAPSHOT.jar /opt/e-wallet
EXPOSE 8080
CMD ["java", "-jar", "/opt/e-wallet/e-wallet-0.0.1-SNAPSHOT.jar"]