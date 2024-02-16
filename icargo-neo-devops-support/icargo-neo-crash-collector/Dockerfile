#FROM rclone/rclone
#RUN apk add --no-cache inotify-tools curl bash

FROM harbor.ibsplc.com/icargo/icargo-neo-crash-collector:base_v2

WORKDIR /app
COPY src/run.sh /app/run.sh
RUN chmod +x /app/run.sh

ENTRYPOINT ["/app/run.sh"]
