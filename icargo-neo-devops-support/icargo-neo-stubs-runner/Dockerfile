FROM maven:3.8-jdk-11
COPY . .
WORKDIR /src
ADD src/etc/settings.xml /usr/share/maven/conf/
# cache the stub-runner in the image as the size is big @See run-stubs.sh for the the version, this also helps in warming up maven with essesntial dependencies
RUN mvn --batch-mode dependency:get -Dtransitive=false -Dartifact=org.springframework.cloud:spring-cloud-contract-stub-runner-boot:2.2.6.RELEASE:jar
ENTRYPOINT ["/bin/bash", "-c", "/src/run-stubs.sh"]
EXPOSE 3000-10000
