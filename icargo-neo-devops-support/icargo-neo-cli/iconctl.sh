#!/usr/bin/env bash
#set -x

# Enable job control for this shell
set -m

CURRDIR=`echo $0 | awk '$0 ~ /^\// { print }'`
if [[ ${CURRDIR} != "" ]]; then
  CURRDIR=`dirname $0`
else
  CURRDIR="`pwd``dirname $0 | cut -c2-`"
fi

export CURRDIR

typeset -r ICONETC="${CURRDIR}/etc/iconctl.conf"

# source the configurations from iconctl conf
source <(grep '#- ' "${ICONETC}" | sed 's/#- //')

typeset -r ENABLE_COLOR="${ENABLE_COLOR:-true}"
typeset HELM_TMOUT="${HELM_TMOUT_DEFAULT:-240s}"
typeset -r HELM_REFRESH='false'

typeset -r ICONSRPT=$(basename ${0})
typeset -r RED_F="\033[1;31m"
typeset -r BLUE_F="\033[1;34m"
typeset -r YELLOW_F="\033[1;33m"
typeset -r GREEN_F="\033[1;32m"
typeset -r NC='\033[0m'
typeset -r WHITE_F='\033[1;37m'

# instance.conf column mapping
typeset -r CONF_ENV='$1'
typeset -r CONF_APPNAME='$2'
typeset -r CONF_K8SCTX='$3'
typeset -r CONF_APPNS='$4'
typeset -r CONF_ENVVALFILE='$5'
typeset -r CONF_HLMCHRT='$6'
typeset -r CHRT_BKUPNAME='._backup_chart.yaml_txt'

# prints an error message to screen
echoe(){
  local TMS=$(date '+%Y-%m-%d %H:%M:%S')
  if [[ ${ENABLE_COLOR} == 'true' ]]; then
    echo -e "${RED_F}[${TMS}] ${*}${NC}"
  else
    echo "[${TMS}] [ERROR] ${*}"
  fi
}

# prints an info message to screen
echoi(){
  local TMS=$(date '+%Y-%m-%d %H:%M:%S')
  if [[ ${ENABLE_COLOR} == 'true' ]]; then
    echo -e "${GREEN_F}[${TMS}] ${*}${NC}"
  else
    echo "[${TMS}] [INFO] ${*}"
  fi
}

# prints an warning message to screen
echow(){
  local TMS=$(date '+%Y-%m-%d %H:%M:%S')
  if [[ ${ENABLE_COLOR} == 'true' ]]; then
    echo -e "${YELLOW_F}[${TMS}] ${*}${NC}"
  else
    echo "[${TMS}] [WARNING] ${*}"
  fi
}

# Print a status line ( used as a separator )
println(){
   [[ ${NOPTR} == "true" ]] && return 0;
   if [[ ${ENABLE_COLOR} == 'true' ]]; then
      echo -e "${WHITE_F}------------------------------${1}------------------------------${NC}"
   else
      echo "------------------------------${1}------------------------------"
   fi
}

displayUsage(){
  echo -e "
  ${WHITE_F}iCargo Neo Administration CLI${NC}
  ------------------------------

  Deployment :
  ${WHITE_F}deploy-single${NC} : Used to deploy a single artifact to the k8s cluster. (${ICONSRPT} deploy-single <environment-name> <artifact-id> <bom yml file>)
  ${WHITE_F}deploy-full${NC} : Used to deploy complete artifacts to the k8s cluster. (${ICONSRPT} deploy-full <environment-name> <bom yml file>)
  ${WHITE_F}app-version${NC} : Displays the current application version deployed in the environment. (${ICONSRPT} app-version <environment-name> <deployment-type (single|full)> <deployment-name (required for type single)>)
  ${WHITE_F}start-application${NC} : Start the application instance after scale down operation. (${ICONSRPT} start-application <environment-name>)
  ${WHITE_F}stop-application${NC} : Stop the application instance. (${ICONSRPT} stop-application <environment-name>)
"
}

assertExit(){
   local ANSRET=${1}
   local EXTMSG="${2}"
   if [[ ${ANSRET} -ne 0 ]]; then
      [[ -n ${EXTMSG} ]] && echoe ${EXTMSG}
      echoe "Unable to continue .. "
      exit ${ANSRET} 
   fi
}

# Retrieves the indexed entry from the conf
lookupConf(){
   local TYPE="$1"
   local ENTRYIDX="$2"
   local ENTRYNAM="$3"
   ENTRYVAL=$(awk '$0 !~ /^#/ && '${CONF_ENV}' == '\"${TYPE}\"' { print '${ENTRYIDX}' }' ${ICONETC})
   typeset -i ANS=$?
   if [[ ${ANS} -ne 0 || ${ENTRYVAL} == "" ]]; then
      echoe "Unable to find the ${ENTRYNAM} (idx ${ENTRYIDX}) for type ${TYPE}"
      return 1
   fi
   echo $ENTRYVAL
   return 0
}

lookupAllEntries(){
   ENTRYIDX="$1"
   ENTRYNAM="$2"
   ENTRYVAL=$(awk '$0 !~ /^#/ { print '${ENTRYIDX}' }' ${ICONETC})
   typeset -i ANS=$?
   if [[ ${ANS} -ne 0 || ${ENTRYVAL} == "" ]]; then
      echoe "Unable to find the ${ENTRYNAM} (idx ${ENTRYIDX})"
      return 1
   fi
   UNIQENTRYVAL=$(echo $ENTRYVAL | xargs -n1 | sort | uniq | xargs)
   echo ${UNIQENTRYVAL}
   return 0
}

# this function will watch for the creation of new pod when a service is updated.
# when a new pod is found (for a given service), then its deployment log is captured
watchAndLogNewInstance(){
	local K8SCTX="${1}"
	local APPNS="${2}"
	local ARTIDR="${3}"
	local WRKDIR="${4}"
	local LOG_FILE=${WRKDIR}/${ARTIDR}"_new.log"

	# capture the pod-name of the service which is currently running
	CUR_SVC_POD=$( kubectl get pods --context="${K8SCTX}" --namespace ${APPNS} --selector "app=${ARTIDR}"  --no-headers | awk '{print $1}' )
	echo "Currently running pod name for " ${APPNS} " is " ${CUR_SVC_POD} >${LOG_FILE} 2>&1


	# staring a watcher, which keeps an eye on whether the creation of new node is stared.
	# if yes, then captures the log of the new node.
	echo "Starting watch at " $(date) >>${LOG_FILE} 2>&1
	a=0
	# loop, just to make sure that it does not run infinitely.
	while [ $a -lt 30 ]
	do
		echo "Counter " $a
		sleep 2
		NEW_SVC_POD=$( kubectl get pods --context=${K8SCTX} --namespace ${APPNS} --selector "app=${ARTIDR}"   --no-headers | grep -v ${CUR_SVC_POD} | awk '{print $1}' )
		if [[ ${NEW_SVC_POD} != ""  ]]
		then
			echo "Watch completed at " $(date) >>${LOG_FILE} 2>&1
			sleep 20
			echo "Newly created pod name is " ${NEW_SVC_POD} >>${LOG_FILE} 2>&1
			# gets the log specific to the newly created pod
			kubectl logs --context="${K8SCTX}" --namespace="${APPNS}" ${NEW_SVC_POD} --follow=true --ignore-errors=true --pod-running-timeout=120s --tail=-1 >>${LOG_FILE} 2>&1
			break
		fi

		# increment the value
		a=`expr $a + 1`
	done
}


# Resolves the application version - this is the buildNumber or artifactVersion depending on full deployment or incremental
resolveAppVersionFromBom(){
  local BOM_FILE="${1}"
  local BOM_TYPE="${2:-single}"
  if [[ ${BOM_TYPE} == 'full' ]]; then
    APP_VER=$(grep '^buildNumber:' ${BOM_FILE} | grep -o '".*"' | sed 's/"//g')
    typeset -i ANS=${?}
  else
    APP_VER=$(grep 'artifactVersion:' ${BOM_FILE} | grep -o '".*"' | sed 's/"//g')
    typeset -i ANS=${?}
  fi
  if [[ ${ANS} -ne 0 || -z ${APP_VER} ]]; then
    return 1
  else
    echo "${APP_VER}"
    return 0
  fi
}

# Resolves the current application version from the helm history command
resolveCurrentAppVersion(){
  local ENV_NAME="${1}"
  local DEP_TYP="${2}"
  local DEP_NAM="${3}"
  # Check arguments
  if [[ -z ${ENV_NAME} || -z ${DEP_TYP} ]]; then
    echoe "EnvironmentName and DeploymentType (single|full) is required."
    return 1
  fi
  isValidEnv ${ENV_NAME}
  K8SCTX=$(lookupConf ${ENV_NAME} ${CONF_K8SCTX} 'K8sContext')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the K8s Context'
  APPNAM=$(lookupConf ${ENV_NAME} ${CONF_APPNAME} 'AppName')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the helm Application name'
  if [[ ${DEP_TYP} == 'single' ]]; then
    test -n "${DEP_NAM}"
    typeset -i ANS=${?}
    assertExit ${ANS} 'DeploymentName is required for DeploymentType single'
    APPNAM="${APPNAM}-${DEP_NAM}"
  fi
  CURR_APPVER=$(helm history --output=yaml --max=1 --kube-context=${K8SCTX} --namespace=default ${APPNAM} | awk '/app_version/ { print $NF }' | sed 's/"//g')
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 || -z ${CURR_APPVER} ]]; then
    return 1
  else
    echo "${CURR_APPVER}"
    return 0
  fi
}

# Updates the application version in the helm Chart.yaml file for application version tracking
# Arguments
# 1 - BOM File, 2 - DeploymentType (full|single), 3 - ChartDirectory
updateAppVersionInChart(){
  local BOM_FILE="${1}"
  local DEP_TYPE="${2}"
  local HLMCHRT_DIR="${3}"
  APP_VER=$(resolveAppVersionFromBom "${BOM_FILE}" "${DEP_TYPE}")
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echow "Unable to resolve application version from bom file : ${BOM_FILE} of deployment type ${DEP_TYPE}"
    return 1
  fi
  local HLMCHRT_FILE="${HLMCHRT_DIR}/Chart.yaml"
  if [[ -f ${HLMCHRT_FILE} ]]; then
    cp -p "${HLMCHRT_FILE}" "${HLMCHRT_DIR}/${CHRT_BKUPNAME}"
    sed -i "s/^appVersion:.*$/appVersion: \"${APP_VER}\"/" "${HLMCHRT_FILE}"
    typeset -i ANS=${?}
    return ${ANS}
  else
    echow "Chart.yaml file absent location ${HLMCHRT_FILE}"
    return 1
  fi
}

resetChartChanges(){
  local HLMCHRT_DIR="${1}"
  local BKUP_CHRT="${HLMCHRT_DIR}/${CHRT_BKUPNAME}"
  [[ -f ${BKUP_CHRT} ]] && mv ${BKUP_CHRT} "${HLMCHRT_DIR}/Chart.yaml"
}

preDeployOperations(){
  set +x
  local ENVNAM="${1}"
  # perform login to the cloud instance
  # Using generic user
  # Refresh chart repo
  if [[ ${HELM_REFRESH} == 'true' ]]; then
    echoi "Refreshing helm chart git repository ${HLMCHRT}"
    HLMCHRT=$(lookupConf ${ENVNAM} ${CONF_HLMCHRT} 'EnvHelmDir')
    if [[ -d ${HLMCHRT} ]]; then
       git -C ${HLMCHRT} reset --hard --quiet
       git -C ${HLMCHRT} pull origin master --quiet
    fi
  fi
  return 0
}

postDeployOperations(){
  local ENVNAM="${1}"
  local ARTIDR="${2}"
  local WRKDIR="${3}"
  local DEPSTA="${4}"
  local BOMFILE="${5}"
  # kill all the background jobs
  stopAllJobs
  # Do restart for incremental deployment only
  if [[ ${ARTIDR} != '-' ]]; then
    # Restart deployments as part of this deployment side effect
    if [[ ${DEPSTA} -eq 0 ]]; then
      SRVTYP=$(grep serviceType ${BOMFILE} | grep -oP '"\K[^"\047]+(?=["\047])')
      typeset -i ANS=${?}
      if [[ ${ANS} -eq 0 ]]; then
        local HELM_INSTALL_LOG="${WRKDIR}/helm_install.log"
        echo -e '\n\n' >> ${HELM_INSTALL_LOG}
        case ${SRVTYP} in
        'DOMAIN_SERVICE'| 'WEB_GW' | 'WEB_FE' | 'EBL')
                 echoi "No services to be restarted as part of ${SRVTYP} deployment".
                 ;;
        'WEB_BFF')
                echoi "Restarting all gateway services as part of bff deployment ..."
                restartDeployments ${ENVNAM} 'web_gw' | tee -a ${HELM_INSTALL_LOG}
                ;;
        'INTERNAL_SERVICE')
                # check if this is config server deployment
                if [[ ${ARTIDR} = *-config-server ]]; then
                  echoi "Config server deployed...restarting domain_service, web_bff, web_gw"
                  restartDeployments ${ENVNAM} 'ebl' | tee -a ${HELM_INSTALL_LOG}
                  restartDeployments ${ENVNAM} 'domain_service' | tee -a ${HELM_INSTALL_LOG}
                  restartDeployments ${ENVNAM} 'web_bff' | tee -a ${HELM_INSTALL_LOG}
                  restartDeployments ${ENVNAM} 'web_gw' | tee -a ${HELM_INSTALL_LOG}
                fi
                ;;
        *)
              echow "Unexpected option ${SRVTYP}"
              ;;
        esac
      else
        echoe "Error while resolving service type from Bom file ${BOMFILE}"
      fi
    fi
  fi
  # deployment logs to be shared to dev
  local TRCZIP="${CURRDIR}/`basename ${WRKDIR}`.zip"
  [[ -e ${TRCZIP} ]] && rm ${TRCZIP}
  zip -qr "${TRCZIP}" "${WRKDIR}/" && rm -rf ${WRKDIR}
  echoi "Application deployment logs ${TRCZIP}"
  if [[ ${DEPSTA} -eq 0 ]]; then
    echoi "Deployment successful."
    return 0
  else
    echoe "Deployment failed."
    return 1
  fi
  # report the status back to user
}

waitTillExistingDeploymentOver(){
  local APPNAM="$1"
  local K8SCTX="$2"
  typeset -i ANS=0
  typeset -i CNT=0
  while [[ ${ANS} -eq 0 && ${CNT} -le 10 ]]; do
     HSTATUS=$(helm status --output=table --kube-context=${K8SCTX} --namespace=default ${APPNAM} 2>&1)
     typeset -i ANS=${?}
     [[ ${ANS} -eq 0 ]] && HSTATUS=$(helm status --output=table --kube-context=${K8SCTX} --namespace=default ${APPNAM} | grep 'STATUS' | awk '{print $2}')
     CNT=$((CNT + 1))
     echoi "Helm deployment status for application ${APPNAM} is ${HSTATUS}"
     # if there is an error checking status, check if the release is not found ie deploying it for the first time.
     if [[ ${ANS} -ne 0 ]]; then
       echo ${HSTATUS} | grep -qw 'release: not found'
       typeset -i NFND=${?}
       if [[ ${NFND} -eq 0 ]]; then
         return 0
       fi
     fi
     # todo need to check the various breakout codes
     if [[ ${HSTATUS} == 'deployed' || ${HSTATUS} == 'failed' ]]; then
       return 0
     fi
     sleep 10
  done
  return 1
}

resolveHelmDeploymentStatus(){
  local APPNAM="$1"
  local K8SCTX="$2"
  HSTATUS=$(helm status --output=table --kube-context=${K8SCTX} --namespace=default ${APPNAM} | grep 'STATUS' | awk '{print $2}')
  if [[ ${HSTATUS} != 'deployed' ]]; then
    echoe "Helm deployment status is ${HSTATUS}"
    return 1
  fi
  HSTATUS=$(helm history --max=1 --output=table --kube-context=${K8SCTX} --namespace=default ${APPNAM} | tail -1 | awk '{print $NF}')
  if [[ ${HSTATUS} != 'complete' ]]; then
     echoe "Helm deployment state is ${HSTATUS}"
     return 1
  fi
}

isValidEnv(){
  local ENVNAM_TMP="${1}"
  ALLENV=$(lookupAllEntries ${CONF_ENV} 'Environments')
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echoe "Unable to lookup environments."
    exit 1
  else
    echo $ALLENV | grep -qw $ENVNAM_TMP
    typeset -i ANS=${?}
    if [[ ${ANS} -ne 0 ]]; then
	    echoe "Invalid environment name ${ENVNAM_TMP}"
       exit 1
    fi
  fi
  return 0
}

stopAllJobs(){
  local JOBS=$(jobs -p)
  if [[ -n ${JOBS} ]]; then
    kill -TERM ${JOBS}
  fi
}

restartDeployments(){
  local ENVNAM="${1}"
  local DTYPE="${2}"
  local WAIT="${3:-true}"
  K8SCTX=$(lookupConf ${ENVNAM} ${CONF_K8SCTX} 'K8sContext')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the K8s Context'
  APPNS=$(lookupConf ${ENVNAM} ${CONF_APPNS} 'AppNamespace')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Application Namespace'
  DEPLOYMENTS=$(kubectl get deployments.apps --context="${K8SCTX}" --namespace="${APPNS}" --output='custom-columns=NAME:.metadata.name' --selector="srvtyp=${DTYPE}" )
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echoe "Unable to find deployments of type \"${DTYPE}\""
    echoe "$DEPLOYMENTS"
    return 0
  fi
  for DEP in ${DEPLOYMENTS} ; do
    # skip the header
    if [[ ${DEP} == 'NAME' ]]; then
      continue
    fi
    echoi "Restarting deployment ${DEP} ..."
    kubectl rollout restart deployment --context="${K8SCTX}" --namespace="${APPNS}" "${DEP}"
    # Wait for the deployment to restarted, to prevent overloading the nodes
    if [[ ${WAIT} == 'true' ]]; then
      ATTEMPTS=0
      ROLLOUT_STATUS_CMD="kubectl rollout status deployment --context=${K8SCTX} --namespace=${APPNS} ${DEP}"
      ${ROLLOUT_STATUS_CMD}
      typeset -i RANS=${?}
      # Give it a max of 2 mins to complete
      until [[ ${RANS} -eq 0 || ${ATTEMPTS} -eq 24  ]]; do
        sleep 5
        ${ROLLOUT_STATUS_CMD}
        typeset -i RANS=${?}
        ATTEMPTS=$((ATTEMPTS + 1))
      done
    fi
  done
}

# function to deploy the full application into the cluster
deployFullApp(){
  #set -x
  local ENVNAM="${1}"
  local BOMFILE="${2}"
  if [[ ! -r ${BOMFILE} ]]; then
    echoe "Unable to read BOM file ${BOMFILE}"
    return 1
  fi
  isValidEnv ${ENVNAM}
  preDeployOperations ${ENVNAM}
  K8SCTX=$(lookupConf ${ENVNAM} ${CONF_K8SCTX} 'K8sContext')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the K8s Context'
  APPNS=$(lookupConf ${ENVNAM} ${CONF_APPNS} 'AppNamespace')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Application Namespace'
  APPNAM=$(lookupConf ${ENVNAM} ${CONF_APPNAME} 'AppName')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the helm Application name'
  ENVVALFILETMP=$(lookupConf ${ENVNAM} ${CONF_ENVVALFILE} 'EnvHelmValue')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Env specific Value File'
  local ENVVALFILE=$(eval echo ${ENVVALFILETMP})
  local WRKDIR="${CURRDIR}/${ENVNAM}"
  [[ -d ${WRKDIR} ]] && rm -rf ${WRKDIR}
  mkdir -p ${WRKDIR}
  typeset -i ANS=${?}
  assertExit ${ANS} "Unable to create work directory ${WRKDIR}"
  HLMCHRTTMP=$(lookupConf ${ENVNAM} ${CONF_HLMCHRT} 'EnvHelmDir')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable lookup helm chart directory'
  local HLMCHRT=$(eval echo ${HLMCHRTTMP})
  if [[ ! -d ${HLMCHRT} ]]; then
     echoe "Helm chart directory does not exist ${HLMCHRT}"
     return 1
  fi
  # Verify session to see if auth is success
  kubectl get pods --context=${K8SCTX} --namespace=kube-system >/dev/null
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echoe "Unable to connect to k8s cluster, context ${K8SCTX}"
    return 1
  fi
  echo -e "Kubernetes Context : ${K8SCTX} \n Environment : ${ENVNAM} \n DeploymentName : ${APPNAM} \n DeploymentNS : ${APPNS}" >>"${WRKDIR}/helm_install.log"
  # Check if an ongoing deployment is in progress
  waitTillExistingDeploymentOver ${APPNAM} ${K8SCTX}
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]] ; then
    echoe "Current deployment status is not ready."
    helm status --output=table --kube-context=${K8SCTX} --namespace=default ${APPNAM} >>"${WRKDIR}/helm_install.log"
    helm history --output=table --max=10 --kube-context=${K8SCTX} --namespace=default ${APPNAM} >>"${WRKDIR}/helm_install.log"
    postDeployOperations ${ENVNAM} '-' ${WRKDIR} 1
    return 1
  fi
  # Update the application version in helm Chart.yaml helm does not support overriding this from command line yet.
  updateAppVersionInChart "${BOMFILE}" 'full' "${HLMCHRT}"
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to update application version in Chart.yaml'
  local OVERRIDES=''
  HELM_TMOUT='900s' # Set a higher timeout for the full deployment ...
  echoi "Installing helm charts ... Timeout : ${HELM_TMOUT} "
  helm upgrade --dry-run --install -f="${ENVVALFILE}" -f="${BOMFILE}" ${OVERRIDES} --atomic --timeout=600s --namespace=default --kube-context=${K8SCTX} ${APPNAM} ${HLMCHRT} >"${WRKDIR}/${ENVNAM}_k8s.yml" 2>&1
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echoe "Error during the helm dryrun phase."
    resetChartChanges "${HLMCHRT}"
    postDeployOperations ${ENVNAM} '-' ${WRKDIR} 1
    return ${?}
  fi
  echo "helm upgrade --install -f=${ENVVALFILE} -f=${BOMFILE} ${OVERRIDES} --atomic --timeout=${HELM_TMOUT} --namespace=default --kube-context=${K8SCTX} ${APPNAM} ${HLMCHRT}"
  helm upgrade --install -f="${ENVVALFILE}" -f="${BOMFILE}" ${OVERRIDES} --atomic --timeout=${HELM_TMOUT} --namespace=default --kube-context=${K8SCTX} ${APPNAM} ${HLMCHRT} >>"${WRKDIR}/helm_install.log" 2>&1 &
  typeset -i HLMUPDPID=${!}
  typeset -i HLMJOBID=$(jobs -l | grep "${HLMUPDPID}" | awk '{print $1}' | grep -o '[0-9]')
  echoi "Setting watches for logs ..."
  kubectl logs --context="${K8SCTX}" --namespace="${APPNS}" --follow=true --ignore-errors=true --pod-running-timeout=120s --tail=-1 >"${WRKDIR}/deploy_new.log" 2>&1 &
  echoi "Setting watches for pods ..."
  kubectl get pods --context="${K8SCTX}" --namespace="${APPNS}" --watch >>"${WRKDIR}/helm_install.log" 2>&1 &
  # bring the process to foreground and wait for the completion or timeout
  if [[ -n ${HLMJOBID} ]]; then
    fg %${HLMJOBID}
  fi
  kubectl describe all --context="${K8SCTX}" --namespace="${APPNS}" >"${WRKDIR}/describe.log" 2>&1
  resolveHelmDeploymentStatus ${APPNAM} ${K8SCTX}
  typeset -i DEPANS=${?}
  echoi "Deployment Status is : ${DEPANS}"
  if [[ ${DEPANS} -eq 0 ]]; then
    kubectl get ingresses --context="${K8SCTX}" --namespace="${APPNS}" >"${WRKDIR}/ingresses.txt" 2>&1 &
    kubectl describe ingresses --context="${K8SCTX}" --namespace="${APPNS}" >>"${WRKDIR}/ingresses.txt" 2>&1 &
  fi
  # Reset the edit done in updateAppVersionInChart
  resetChartChanges "${HLMCHRT}"
  postDeployOperations ${ENVNAM} '-' ${WRKDIR} ${DEPANS} ${BOMFILE}
  echoi "Restarting all gateway services as part of bff deployment ..."
  restartDeployments ${ENVNAM} 'web_gw' | tee -a "helm_install.log"
  return ${DEPANS}
}

# function to deploy a single app into the cluster with rollback support.
deploySingleApp(){
  #set -x
  local ENVNAM="${1}"
  local ARTIDR="${2}"
  local BOMFILE="${3}"
  if [[ ! -r ${BOMFILE} ]]; then
    echoe "Unable to read BOM file ${BOMFILE}"
    return 1
  fi
  isValidEnv ${ENVNAM}
  if [[ -z ${ARTIDR} ]]; then
    echoe "Artifact Id is not specified"
    return 1
  fi
  preDeployOperations ${ENVNAM}
  K8SCTX=$(lookupConf ${ENVNAM} ${CONF_K8SCTX} 'K8sContext')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the K8s Context'
  APPNS=$(lookupConf ${ENVNAM} ${CONF_APPNS} 'AppNamespace')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Application Namespace'
  APPNAM=$(lookupConf ${ENVNAM} ${CONF_APPNAME} 'AppName')
  APPNAM="${APPNAM}-${ARTIDR}" # for incremental deployment suffix it with the artidr ( each artidr is a separate deployment)
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the helm Application name'
  ENVVALFILETMP=$(lookupConf ${ENVNAM} ${CONF_ENVVALFILE} 'EnvHelmValue')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Env specific Value File'
  local ENVVALFILE=$(eval echo ${ENVVALFILETMP})
  local WRKDIR="${CURRDIR}/${ENVNAM}_${ARTIDR}"
  [[ -d ${WRKDIR} ]] && rm -rf ${WRKDIR}
  mkdir -p ${WRKDIR}
  typeset -i ANS=${?}
  assertExit ${ANS} "Unable to create work directory ${WRKDIR}"
  HLMCHRTTMP=$(lookupConf ${ENVNAM} ${CONF_HLMCHRT} 'EnvHelmDir')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable lookup helm chart directory'
  local HLMCHRT=$(eval echo ${HLMCHRTTMP})
  if [[ ! -d ${HLMCHRT} ]]; then
     echoe "Helm chart directory does not exist ${HLMCHRT}"
     return 1
  fi
  # Verify session to see if auth is success
  kubectl get pods --context=${K8SCTX} --namespace=kube-system >/dev/null
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echoe "Unable to connect to k8s cluster, context ${K8SCTX}"
    return 1
  fi
  echo -e "Kubernetes Context : ${K8SCTX} \n Environment : ${ENVNAM} \n DeploymentName : ${APPNAM} \n DeploymentNS : ${APPNS}" >>"${WRKDIR}/helm_install.log"
  #Tail the existing container logs for reference
  echoi "Collecting current application logs for service ${ARTIDR} ..."
  kubectl logs --context="${K8SCTX}" --namespace="${APPNS}" --selector "app=${ARTIDR}" --follow=false --ignore-errors=true --pod-running-timeout=30s --tail=500 >"${WRKDIR}/${ARTIDR}_old.log" 2>&1
  # Check if an ongoing deployment is in progress
  waitTillExistingDeploymentOver ${APPNAM} ${K8SCTX}
  
  # pushing the function to watch and log the newly creating pod, to background
  watchAndLogNewInstance ${K8SCTX} ${APPNS} ${ARTIDR} ${WRKDIR} &
  
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]] ; then
    echoe "Current deployment status is not ready."
    helm status --output=table --kube-context=${K8SCTX} --namespace=default ${APPNAM} >>"${WRKDIR}/helm_install.log"
    helm history --output=table --max=10 --kube-context=${K8SCTX} --namespace=default ${APPNAM} >>"${WRKDIR}/helm_install.log"
    postDeployOperations ${ENVNAM} ${ARTIDR} ${WRKDIR} 1
    return 1
  fi
  # Update the application version in helm Chart.yaml helm does not support overriding this from command line yet.
  updateAppVersionInChart "${BOMFILE}" 'single' "${HLMCHRT}"
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to update application version in Chart.yaml'
  # Don`t create ingress and namespaces as part of incremental deployment these has to be setup prior, since this helm deployment is not the owner of shared objects
  local OVERRIDES='--set disable.ns=true'
  echoi "Installing helm charts ..."
  helm upgrade --dry-run --install -f="${ENVVALFILE}" -f="${BOMFILE}" ${OVERRIDES} --atomic --timeout=240s --namespace=default --kube-context=${K8SCTX} ${APPNAM} ${HLMCHRT} >"${WRKDIR}/${ENVNAM}_k8s.yml" 2>&1
  typeset -i ANS=${?}
  if [[ ${ANS} -ne 0 ]]; then
    echoe "Error during the helm dryrun phase."
    resetChartChanges "${HLMCHRT}"
    postDeployOperations ${ENVNAM} ${ARTIDR} ${WRKDIR} 1
    return ${?}
  fi
  echo "helm upgrade --install -f=${ENVVALFILE} -f=${BOMFILE} ${OVERRIDES} --atomic --timeout=${HELM_TMOUT} --namespace=default --kube-context=${K8SCTX} ${APPNAM} ${HLMCHRT}"
  helm upgrade --install -f="${ENVVALFILE}" -f="${BOMFILE}" ${OVERRIDES} --atomic --timeout=${HELM_TMOUT} --namespace=default --kube-context=${K8SCTX} ${APPNAM} ${HLMCHRT} >>"${WRKDIR}/helm_install.log" 2>&1 &
  typeset -i HLMUPDPID=${!}
  typeset -i HLMJOBID=$(jobs -l | grep "${HLMUPDPID}" | awk '{print $1}' | grep -o '[0-9]')
  # echoi "Setting watches for logs ..."
  # kubectl logs --context="${K8SCTX}" --namespace="${APPNS}" --selector "app=${ARTIDR}" --follow=true --ignore-errors=true --pod-running-timeout=120s --tail=-1 >"${WRKDIR}/${ARTIDR}_new.log" 2>&1 &
  echoi "Setting watches for pods ..."
  kubectl get pods --context="${K8SCTX}" --namespace="${APPNS}" --selector "app=${ARTIDR}" --watch >>"${WRKDIR}/helm_install.log" 2>&1 &
  # bring the process to foreground and wait for the completion or timeout
  if [[ -n ${HLMJOBID} ]]; then
    fg %${HLMJOBID}
  fi
  kubectl describe all --context="${K8SCTX}" --namespace="${APPNS}" --selector "app=${ARTIDR}" >"${WRKDIR}/${ARTIDR}_describe.log" 2>&1
  resolveHelmDeploymentStatus ${APPNAM} ${K8SCTX}
  typeset -i DEPANS=${?}
  if [[ ${DEPANS} -eq 0 ]]; then
    kubectl get ingresses --context="${K8SCTX}" --namespace="${APPNS}" >"${WRKDIR}/ingresses.txt" 2>&1 &
    kubectl describe ingresses --context="${K8SCTX}" --namespace="${APPNS}" >>"${WRKDIR}/ingresses.txt" 2>&1 &
  fi
  resetChartChanges "${HLMCHRT}"
  postDeployOperations ${ENVNAM} ${ARTIDR} ${WRKDIR} ${DEPANS} ${BOMFILE}
  return ${?}
}

# Function to stop all the application pods, this is typically used to scale down infra during non operating window
stopApplication(){
  local ENV_NAM="${1}"
  isValidEnv ${ENV_NAM}
  K8SCTX=$(lookupConf ${ENV_NAM} ${CONF_K8SCTX} 'K8sContext')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the K8s Context'
  APPNS=$(lookupConf ${ENV_NAM} ${CONF_APPNS} 'AppNamespace')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Application Namespace'
  echoi "Scaling all pods in namespace ${APPNS} to 0 ..."
  kubectl scale deployment --context="${K8SCTX}" --namespace="${APPNS}" --replicas=0 --all
  # Some hardcoded actions
  typeset -r ICO_SYS_NS='icargo-system'
  kubectl get namespace --context="${K8SCTX}" ${ICO_SYS_NS} >/dev/null 2>&1
  typeset -i ANS=${?}
  if [[ ${ANS} -eq 0 ]]; then
    echoi "Proceeding to delete monitoring components in ${ICO_SYS_NS} namespace ..."
    deleteK8sObjectIfPresent "${K8SCTX}" "${ICO_SYS_NS}" 'deployment' 'neo-txprobe-web'
    deleteK8sObjectIfPresent "${K8SCTX}" "${ICO_SYS_NS}" 'daemonset' 'icargo-neo-logstash'
    deleteK8sObjectIfPresent "${K8SCTX}" "${ICO_SYS_NS}" 'daemonset' 'neo-txprobe-aggregator'
    deleteK8sObjectIfPresent "${K8SCTX}" "${ICO_SYS_NS}" 'cronjobs.batch' 'icargo-neo-curator'
  else
    echow "${ICO_SYS_NS} not present"
  fi
  echoi "iCargo application components in namespace ${APP_NS} stopped."
  return 0
}

startApplication(){
  local ENV_NAM="${1}"
  isValidEnv ${ENV_NAM}
  K8SCTX=$(lookupConf ${ENV_NAM} ${CONF_K8SCTX} 'K8sContext')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the K8s Context'
  APPNS=$(lookupConf ${ENV_NAM} ${CONF_APPNS} 'AppNamespace')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Application Namespace'
  ENVVALFILETMP=$(lookupConf ${ENV_NAM} ${CONF_ENVVALFILE} 'EnvHelmValue')
  typeset -i ANS=${?}
  assertExit ${ANS} 'Unable to resolve the Env specific Value File'
  local ENVVALFILE=$(eval echo ${ENVVALFILETMP})

  # Some hardcoded actions
  typeset -r ICO_SYS_NS='icargo-system'
  kubectl get namespace --context="${K8SCTX}" ${ICO_SYS_NS} >/dev/null 2>&1
  typeset -i ANS=${?}
  if [[ ${ANS} -eq 0 ]]; then
    echoi "Proceeding to install icargo-mon application in namespace ${ICO_SYS_NS}"
    helm upgrade --atomic --kube-context="${K8SCTX}" -f "${CURRDIR}/../icargo-neo-helm-monitoring/values.yaml" -f "${ENVVALFILE}" --set disable.ns=false icargo-mon "${CURRDIR}/../icargo-neo-helm-monitoring"
  else
    echow "${ICO_SYS_NS} namespace not present"
  fi
  echoi "Scaling all pods in namespace ${APPNS} to 1 ..."
  kubectl scale deployment --context="${K8SCTX}" --namespace="${APPNS}" --replicas=1 --all
  echoi "iCargo application components in namespace ${APP_NS} started."
  return 0
}

deleteK8sObjectIfPresent(){
  local D_K8SCTX=${1}
  local D_NS="${2}"
  local D_TYP="${3}"
  local D_NAME="${4}"
  kubectl get "${D_TYP}" --context="${D_K8SCTX}" --namespace="${D_NS}" "${D_NAME}" >/dev/null 2>&1
  typeset -i ANS=${?}
  if [[ ${ANS} -eq 0 ]]; then
    echoi "Deleting ${D_TYP} ${D_NAME} in ${D_NS} ..."
    kubectl delete "${D_TYP}" --context="${D_K8SCTX}" --namespace="${D_NS}" "${D_NAME}"
    return ${?}
  fi
  return 0
}


# main block

ICON_ACT="${1}"
ICON_ENV="${2}"
ICON_ARG1="${3}"
ICON_ARG2="${4}"

case ${ICON_ACT} in

   'deploy-single')
         deploySingleApp "${ICON_ENV}" "${ICON_ARG1}" "${ICON_ARG2}"
         exit ${?}
         ;;
   'deploy-full')
         deployFullApp "${ICON_ENV}" "${ICON_ARG1}"
         exit ${?}
         ;;
   'app-version')
        resolveCurrentAppVersion "${ICON_ENV}" "${ICON_ARG1}" "${ICON_ARG2}"
        exit ${?}
        ;;
   'start-application')
        startApplication "${ICON_ENV}"
        exit ${?}
        ;;
  'stop-application')
       stopApplication "${ICON_ENV}"
       exit ${?}
       ;;
   *)
         # check if its a jvm command
   	 echoe "Invalid command : ${ICON_ACT}"
   	 displayUsage
         ;;
esac
