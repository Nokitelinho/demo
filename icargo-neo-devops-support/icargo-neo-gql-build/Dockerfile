FROM maven:3.8-jdk-11
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash -
RUN apt-get install -y nodejs

WORKDIR /app

COPY . .
RUN cd bootstrap-maven && mvn --batch-mode clean deploy
ADD src/etc/settings.xml /usr/share/maven/conf/
RUN cd /app && npm --no-progress install
ENTRYPOINT ["node", "src/server.js"]
