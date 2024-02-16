#!/usr/bin/env bash

echo "Setting up build for neo-stubs-runner pipeline ..."
set -e

DOCKER_REG="${1}"
SERVICE_NAME="icargo-neo-stubs-runner"
SERVICE_VERSION="1.2.8" # This version needs to be updated manually
docker build --tag "${DOCKER_REG}/${SERVICE_NAME}:${SERVICE_VERSION}" .
echo "Docker build completed successfully."
docker push "${DOCKER_REG}/${SERVICE_NAME}:${SERVICE_VERSION}"
echo "Docker Image pushed to remote registry. Image : ${DOCKER_REG}/${SERVICE_NAME}:${SERVICE_VERSION}"
