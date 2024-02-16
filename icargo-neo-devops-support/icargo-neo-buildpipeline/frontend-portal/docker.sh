echo "Creating and pushing docker image..."
set -e

DOCKER_REG="${1}"
SERVICE_NAME="${2}"
APP_DIR="${3}"
GIT_COMMIT_ID=$(git log -1 --pretty=%H | cut -c -10)
echo "GIT_COMMIT_ID : ${GIT_COMMIT_ID}"
GIT_COMMIT_MSG=$(git log -1 --pretty='%cn : %s')
echo "GIT_COMMIT_MSG : ${GIT_COMMIT_MSG}"

IFS=',' read -ra SERVICE_NAME_LIST <<< "${SERVICE_NAME}"

if [ "${#SERVICE_NAME_LIST[@]}" -gt 1 ]
then
    
    for serviceName in "${SERVICE_NAME_LIST[@]}"
    do
        echo "Processing $serviceName"
        if [[ "$serviceName" == *"cvc"* ]]
        then   
            docker build --tag "${DOCKER_REG}/${serviceName}:${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_ID="${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_MSG="${GIT_COMMIT_MSG}" --build-arg OUTPUT_PATH="cvc" --build-arg CONTEXT_PATH="cvcportal" --build-arg APP_DIR="${APP_DIR}" .
            echo "cvcportal docker build completed successfully."
        else
            docker build --tag "${DOCKER_REG}/${serviceName}:${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_ID="${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_MSG="${GIT_COMMIT_MSG}" --build-arg OUTPUT_PATH="ico" --build-arg CONTEXT_PATH="icargoneoportal" --build-arg APP_DIR="${APP_DIR}" .
            echo "icargoneoportal docker build completed successfully."
        fi        
        docker push "${DOCKER_REG}/${serviceName}:${GIT_COMMIT_ID}"
        echo "Docker Image pushed to remote registry. Image : ${DOCKER_REG}/${serviceName}:${GIT_COMMIT_ID}"
    done
else
	if [[ "${SERVICE_NAME}" == "cvc-frontend-portal" ]]
        then   
            docker build --tag "${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_ID="${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_MSG="${GIT_COMMIT_MSG}" --build-arg OUTPUT_PATH="dist" --build-arg CONTEXT_PATH="cvcportal" --build-arg APP_DIR="${APP_DIR}" .
            echo "cvcportal docker build completed successfully."
	else
		docker build --tag "${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_ID="${GIT_COMMIT_ID}" --build-arg GIT_COMMIT_MSG="${GIT_COMMIT_MSG}" --build-arg OUTPUT_PATH="dist" --build-arg CONTEXT_PATH="icargoneoportal" --build-arg APP_DIR="${APP_DIR}" .
		echo "icargoneoportal docker build completed successfully."
	fi
    docker push "${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}"
    echo "Docker Image pushed to remote registry. Image : ${DOCKER_REG}/${SERVICE_NAME}:${GIT_COMMIT_ID}"
fi
