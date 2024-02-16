#!/usr/bin/env bash

CURRDIR=`echo $0 | awk '$0 ~ /^\// { print }'`
if [[ ${CURRDIR} != "" ]]; then
  CURRDIR=`dirname $0`
else
  CURRDIR="`pwd``dirname $0 | cut -c2-`"
fi
PID_FILE="${CURRDIR}/service.pid"

startService(){
  cd "${CURRDIR}/.."
  nohup node "src/server.js" > icargo-neo-gql-build.log 2>&1 &
  typeset PID=${!}
  echo ${PID} > "${PID_FILE}"
  echo "Server Started with pid ${PID}"
  cd -
}

stopService(){
  if [[ -r "${PID_FILE}" ]]; then
    local PID=$(cat "${PID_FILE}")
    kill -0 $PID >/dev/null 2>&1
    typeset -i ANS=${?}
    if [[ ${ANS} -eq 0 ]]; then
      echo "Stopping server with PID $PID ..."
      kill -TERM ${PID}
      return ${?}
    else
      echo "[ERROR] Process is not running."
      rm ${PID_FILE}
      return 1
    fi
  else
    echo "[ERROR] Process is not running."
    return 1
  fi
}

# Main block starts here

ACTION=${1}
case ${ACTION} in
  'start')
     startService
     ;;
  'stop')
    stopService
    ;;
  *)
    echo "[ERROR] Invalid command possible actions start|stop"
    ;;
esac
