# Stage 0, Nginx image as base to serve the app
FROM nginx:1.21.6-alpine AS portal-build

# GIT_COMMIT_ID=$(git log -1 --pretty=%s) GIT_COMMIT_MSG=$(git log -1 --pretty='%cn : %s')
ARG GIT_COMMIT_ID=unknown
ARG GIT_COMMIT_MSG=unknown
ARG APP_DIR=unknown
LABEL git.commit.id.abbrev=${GIT_COMMIT_ID}
LABEL git.commit.message.short=${GIT_COMMIT_MSG}

# Create app directory
WORKDIR /usr/src/icargoneo/${APP_DIR}

# Copy nginx configuration
COPY nginx.conf /etc/nginx/nginx.conf

# Copy nginx script file for serving app-config.json
COPY node_modules/@neo/config-utils/src/index.js ../../index.js

# Copying build
COPY dist/applications/ ./

# Internal port in which the portal will run
EXPOSE 8080

# Trying the dummy one 
#Starting the static content with nginx
ENTRYPOINT ["nginx","-g","daemon off;"]
