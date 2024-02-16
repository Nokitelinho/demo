#!/usr/bin/env bash

echo "Setting up build for icargo-neo-crash-collector ..."
set -e

DOCKER_REG="${1}"
if [[ -z ${DOCKER_REG} ]]; then
   echo "[ERROR] Docker Registry is not specified."
   exit 1
fi
SERVICE_NAME="icargo-neo-crash-collector"
SERVICE_VERSION="base_v2" # This version needs to be updated manually
docker build --tag "${DOCKER_REG}/${SERVICE_NAME}:${SERVICE_VERSION}" --file Dockerfile_BaseImage .
echo "Docker build completed successfully."
#docker push "${DOCKER_REG}/${SERVICE_NAME}:${SERVICE_VERSION}"
#echo "Docker Image pushed to remote registry. Image : ${DOCKER_REG}/${SERVICE_NAME}:${SERVICE_VERSION}"

