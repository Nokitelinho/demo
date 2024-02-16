FROM docker.elastic.co/logstash/logstash:7.17.1
RUN rm -f /usr/share/logstash/pipeline/logstash.conf
ADD pipeline/ /usr/share/logstash/pipeline/
ADD config/ /usr/share/logstash/config/

# The udp port in pipeline and api endpoint
EXPOSE 9040/udp 9600/tcp