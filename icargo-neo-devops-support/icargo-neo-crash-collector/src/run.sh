#!/usr/bin/env bash

# Env variables that needs to be set while running the script
# S3_BUCKET
CRASH_REMOTE_DIR="${CRASH_REMOTE_DIR:-/icargo-crash-dumps}"
CRASH_WATCH_DIR="${CRASH_WATCH_DIR:-/icargo-crash-dumps}"
PROMETHEUS_ALERT_MGR_URL="${PROMETHEUS_ALERT_MGR_URL:-http://alertmanager-operated.prometheus.svc.cluster.local:9093/api/v1/alerts}"
PROMETHEUS_ALERT_NS="${PROMETHEUS_ALERT_NS:-prometheus}"

echo "------------------------------------------"
printenv
echo "------------------------------------------"

notifyAlertManager(){
  local DUMP_NAME="${1}"
  local STATUS="${2}"
  DUMP_NAME=$(basename "${DUMP_NAME}")
  # Check if alert manager is configured
  echo "Checking prometheus alert manager is available ..."
  curl --head --silent --connect-timeout 3 ${PROMETHEUS_ALERT_MGR_URL}
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echo "Unable to connect to alert manager url ${PROMETHEUS_ALERT_MGR_URL} ..."
    echo "Alert notification skipped for $DUMP_NAME ..."
    return 1
  fi
  ALERT_NAME="HeapDumpGenerated"
  DESC="Heap Dump generated ${DUMP_NAME} transferred to s3://${S3_BUCKET}${CRASH_REMOTE_DIR}, Check the application health which generated the dump."
  echo "Sending ${STATUS} status for dump $DUMP_NAME ..."
  curl -XPOST ${PROMETHEUS_ALERT_MGR_URL} -d "[{
	  \"status\": \"${STATUS}\",
	  \"labels\": {
		\"alertname\": \"${ALERT_NAME}\", \"namespace\": \"${PROMETHEUS_ALERT_NS}\", \"pod\": \"${HOSTNAME}\", \"service\": \"${DUMP_NAME}\", \"severity\":\"critical\", \"instance\": \"${DUMP_NAME}\"
	  },
	  \"annotations\": {
		  \"summary\": \"${DESC}\", \"description\": \"${DESC}\"
	  },
	  \"generatorURL\": \"http://icargo-neo-crash-collector/nothing-here-to-check\"
  }]"
  return ${?}
}

#
# Script to gzip and ship the dumps to remote s3 location
#
inotifywait --monitor "${CRASH_WATCH_DIR}/" --event close_write --exclude '.gz' | while read pathDir action fileName; do
  TMS=$(date '+%Y%m%d%H%M%S')
  DUMP_FILE="${pathDir}${fileName}"
  DUMP_FILE_GZ="${pathDir}${fileName}_${TMS}.gz"
  notifyAlertManager "${DUMP_FILE_GZ}" 'firing'
  echo "Processing Crash Dump : ${DUMP_FILE}"
  gzip --stdout ${DUMP_FILE} > "${DUMP_FILE_GZ}"
  echo "Moving dump to s3 ${DUMP_FILE_GZ} -> s3://${S3_BUCKET}${CRASH_REMOTE_DIR}/ ..."
  aws s3 mv ${DUMP_FILE_GZ} "s3://${S3_BUCKET}${CRASH_REMOTE_DIR}/"
  typeset -i CPYANS=${?}
  if [[ ${CPYANS} -eq 0 ]]; then
    echo "Dump uploaded to S3 ${S3_BUCKET}${CRASH_REMOTE_DIR}/`basename ${DUMP_FILE_GZ}`"
  else
    echo "ERROR - Error uploading crash dump ${S3_BUCKET}${CRASH_REMOTE_DIR}/`basename ${DUMP_FILE_GZ}`"
  fi
  rm -f ${DUMP_FILE}
  notifyAlertManager "${DUMP_FILE_GZ}" 'resolved'
done
