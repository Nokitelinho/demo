#wladmin start ico_nfr_adm tail

METHOD=$1
DOMAIN_NAME=$2
CLASSIC_LOCATION_URL=$3

_self="${0##*/}"

_DOM=${DOMAIN_NAME}
_ADM=${DOMAIN_NAME}

DIRCMD=/opt/icob
#export USER_WLADMIN_CONF=${DIRCMD}/etc/servers.conf
export ENABLE_COLOR=false
export CURRDIR=${DIRCMD}

#. ${DIRCMD}/libs/setEnv.sh
. ${DIRCMD}/libs/wladmin.functions.sh
. ${DIRCMD}/libs/icoadmin.functions.sh

ICARGO_EAR="icargo.ear"
ICARGO_CONFIG="iCargoConfig.zip"
#RELEASE_SPECIFIC_FOLDER="REF_BUILD_SPECIFIC"
RELEASE_SPECIFIC_FOLDER=""


WLADMIN=${DIRCMD}/wladmin
ICOADMIN=${DIRCMD}/icoadmin


function usage(){
   line="---------------"
   printf "\n\n\n"
   printf "%s   usage   %s\n" $line $line
   echo "${_self} upgrade <domain> <url_to_icargo.ear/icargoConfig.zip>"
   echo "${_self} taillog <domain>"
   echo " "
   printf "%s   example   %s\n" $line $line
   echo "       ${_self} upgrade ico_nfr_dom http://icargobuild01:8080/ReleaseArea4.10/4.10_Master/iCO_4.10.6.0.44/REF_BUILD_SPECIFIC/"
   echo "       ${_self} taillog ico_nfr_dom"
   printf "\n\n\n"
}

function findLandingVersion(){
   local DOMAIN=${1}
   local MYDOMDIR=$(getDomainDirectoryForDomain ${DOMAIN})
   if [[ -d ${MYDOMDIR} ]]; then
      local FILE="${MYDOMDIR}/${LANDING}/icargo.ear"
      if [ -e ${FILE} ]; then
         if [[ $(type -t ${DIRCMD}/icargo-version) ]]; then
            LVERSION=$(${DIRCMD}/icargo-version ${FILE})
            typeset -i ANS=${?}
            if [[ ${ANS} -eq 0 ]]; then
               echo ${LVERSION}
               return 0
            else
               echoe "Unexpected error retriving iCargo landing version."
               return ${ANS}
            fi
         else
            echoe "icargo-version native executable not found in path."
            return 3
         fi
         return 0
      else
         echoe "Landing does not have an icargo.ear"
         return 1
      fi
   else
      echoe "Domain directory ${MYDOMDIR} is not correct." >&2
      return 2
   fi
}


function extractLandingVersion(){
   findLandingVersion $_DOM
}

function extractRunningVersion(){
   retrieveCurrentVersionId $_DOM
}

function stopServer(){
   ${WLADMIN} stop $_ADM force
}

function doDeployment(){
   VER=$1
   ${ICOADMIN} deploy $_DOM $VER
}

function startServer(){
   ${WLADMIN} start $_ADM 
}


function copyDeployablesToLanding(){
   DOMAIN_DIR=$(getDomainDirectoryForDomain ${_DOM})
   _LANDING_DIR=${DOMAIN_DIR}"/user_stage/landing/app"
   printf  "Downloading %s and %s to %s \n" $ICARGO_EAR $ICARGO_CONFIG $_LANDING_DIR
   cd ${_LANDING_DIR}
   _CLASSIC_FOLDER=$CLASSIC_LOCATION_URL"/"$RELEASE_SPECIFIC_FOLDER
   wget -nv -O $ICARGO_CONFIG $_CLASSIC_FOLDER"/"$ICARGO_CONFIG
   wget -nv -O $ICARGO_EAR $_CLASSIC_FOLDER"/"$ICARGO_EAR
}	

function getDataSourceXML(){
   DOMAIN_DIR=$(getDomainDirectoryForDomain ${_DOM})
   XML=$(find ${DOMAIN_DIR}/config/jdbc -name "iCargoDataSource*.xml")
   echo ${XML}

}

function extractDBConnectionURL(){
   XML=$(getDataSourceXML)
   # parse the <url> tag fully from the xml
   URL="$(grep -o -P '(?<=<url>).*(?=</url>)' ${XML} )"
   echo $URL
}

function extractDBUserName(){
   XML=$(getDataSourceXML)
   # parse the <username> tag fully from the xml
   USR_TAG="$(grep -A 1 '<name>user' ${XML} )"
   USERNAME="$(echo ${USR_TAG} | grep -o -P '(?<=<value>).*(?=</value>)'  )"
   echo $USERNAME
}

function writePasswordPython(){
   PY_FILE=$1
   PWD_CRYPT=$2
   DOMAIN_DIR=$(getDomainDirectoryForDomain ${_DOM})
   echo "domain=\"${DOMAIN_DIR}\"" > ${PY_FILE}
   echo "service=weblogic.security.internal.SerializedSystemIni.getEncryptionService(domain)" >> ${PY_FILE}
   echo "encryption=weblogic.security.internal.encryption.ClearOrEncryptedService(service)"  >> ${PY_FILE}
   echo "print encryption.decrypt(\"${PWD_CRYPT}\")"  >> ${PY_FILE}
}

function extractDBPassword(){
   XML=$(getDataSourceXML)
   # parse the <password-encrypted> tag fully from the xml
   PWD_CRYPT="$(grep -o -P '(?<=<password-encrypted>).*(?=</password-encrypted>)' ${XML} )"
   # echo $PWD_CRYPT
   FILE="decryptPassword.py"
   rm -f ${FILE}
   writePasswordPython ${FILE} ${PWD_CRYPT}
   source /opt/weblogic/wlserver/server/bin/setWLSEnv.sh &> /dev/null
   PWD=$(java weblogic.WLST ${FILE} | tail -1 ) 
   echo ${PWD}
   rm -f ${FILE}

}

function doUpgrade(){
   _VERSION=$(extractLandingVersion | tail -1)
   echo "Version is " $_VERSION
   stopServer
   ${WLADMIN} clearlogs $_DOM
   doDeployment ${_VERSION}
   startServer
}

function getTaillogs(){
   DOMAIN_DIR=$(getDomainDirectoryForDomain ${_DOM})
   _LOG_DIR=${DOMAIN_DIR}"/user_stage/logs/wls"
   cd ${_LOG_DIR}
   tail -200f ${_ADM}.out
}

function tailInstanceOutLog(){
   local DOMAIN=$1
   local INSTANCES=$(findAllInstancesForDomain $DOMAIN )
   echo $INSTANCES
   local DOMDIR=$(getDomainDirectoryForDomain ${DOMAIN})
   local LOGROOT=$(getLogDir ${DOMDIR} ${DOMAIN})
   local LOGFILES
   for INST in $INSTANCES
   do
       LOGFILES="$LOGFILES $( find ${LOGROOT}/ -type f \( -name "${INST}.log" -o -name "${INST}.out" \) )"
   done
   echo $LOGFILES
   tail -f $LOGFILES
   return 0
}


#Staring main executions
if [[ ${DOMAIN_NAME} == "" ]] ; then
   usage
   exit 1
fi

case ${METHOD} in 
	'upgrade')
		if [[ ( ${DOMAIN_NAME} == "") || ( ${CLASSIC_LOCATION_URL} == "") ]] ; then
		   usage
		   exit 1
		fi
	        doUpgrade
		;;
	'taillog')
		echo "DOMAIN SAELECTION IS ${DOMAIN_NAME}"
		tailInstanceOutLog ${DOMAIN_NAME}
                ;;
	'download')
		if [[ ( ${DOMAIN_NAME} == "") || ( ${CLASSIC_LOCATION_URL} == "") ]] ; then
                   usage
                   exit 1
                fi
	 	copyDeployablesToLanding ${DOMAIN_NAME}
		exit 1
		;;
	'version-running')
		extractRunningVersion
		exit 1
		;;
	'version-landing')
		extractLandingVersion
                exit 1
                ;;
        'db-url')
                extractDBConnectionURL
                exit 1
                ;;

        'db-username')
                extractDBUserName
                exit 1
                ;;
        'db-password')
                extractDBPassword
                exit 1
                ;;
        'restart')
                ${WLADMIN} stop ${DOMAIN_NAME} force
		${WLADMIN} start ${DOMAIN_NAME} tail
                exit 1
                ;;
        'run-command')
		 "${DOMAIN_NAME}"
                exit 1
                ;;

	*)
		usage
		exit 1
		;;
esac


