FROM docker.elastic.co/logstash/logstash:7.10.1
RUN bin/logstash-plugin install logstash-output-amazon_es

# The udp port in pipeline and api endpoint
EXPOSE 9040/udp 9060/udp 9600/tcp