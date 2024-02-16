echo "Creating and pushing docker image..."
set -e

DOCKER_REG="${1}"
SERVICE_NAME="${2}"
APP_DIR="${3}"
GIT_COMMIT_ID=$(git log -1 --pretty=%H | cut -c -10)
echo "GIT_COMMIT_ID : ${GIT_COMMIT_ID}"
GIT_COMMIT_MSG=$(git log -1 --pretty='%cn : %s')
echo "GIT_COMMIT_MSG : ${GIT_COMMIT_MSG}"

docker build --tag "${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_ID="${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_MSG="${GIT_COMMIT_MSG}"  --build-arg APP_DIR="${APP_DIR}" .
echo "Docker build completed successfully."
docker push "${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}"
echo "Docker Image pushed to remote registry. Image : ${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}"
