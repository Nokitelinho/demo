# Stage 0, Nginx image as base to serve the app
FROM nginx:1.21.6-alpine AS portal-build

# GIT_COMMIT_ID=$(git log -1 --pretty=%s) GIT_COMMIT_MSG=$(git log -1 --pretty='%cn : %s')
ARG GIT_COMMIT_ID=unknown
ARG GIT_COMMIT_MSG=unknown
ARG OUTPUT_PATH=unknown
ARG CONTEXT_PATH=unknown
ARG APP_DIR=unknown
LABEL git.commit.id.abbrev=${GIT_COMMIT_ID}
LABEL git.commit.message.short=${GIT_COMMIT_MSG}

# Create app directory
WORKDIR /usr/src/${CONTEXT_PATH}/${APP_DIR}

# Copy nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy nginx script file for serving app-config.json
COPY node_modules/@neo/config-utils/src/index.js ../../index.js

# Copying build
COPY ${OUTPUT_PATH}/applications/ ./

# Internal port in which the portal will run
EXPOSE 8080

#Starting the static content with nginx
ENTRYPOINT ["nginx","-g","daemon off;"]
