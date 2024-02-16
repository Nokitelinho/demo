#JBADMIN start ico_nfr_adm tail
# /data/jboss_std/landing/icocoe1_jdom01 Ear to be copied 
METHOD=$1
DOMAIN_NAME=$2
CLASSIC_LOCATION_URL=$3

_self="${0##*/}"

_DOM=${DOMAIN_NAME}
_ADM=${DOMAIN_NAME}

DIRCMD=/data/jboss_std/jicob
#export USER_JBADMIN_CONF=${DIRCMD}/etc/servers.conf
export ENABLE_COLOR=false
export CURRDIR=${DIRCMD}

#. ${DIRCMD}/libs/setEnv.sh
#. ${DIRCMD}/libs/wladmin.functions.sh
#. ${DIRCMD}/libs/icoadmin.functions.sh

#echo "Executing the JBADMIN.sh"
. /data/jboss_std/jicob/libs/jbadmin.functions.sh
. /data/jboss_std/jicob/libs/icoadmin.functions.sh
ICARGO_EAR="icargo.ear"
ICARGO_CONFIG="iCargoConfig.zip"
#RELEASE_SPECIFIC_FOLDER="REF_BUILD_SPECIFIC"
RELEASE_SPECIFIC_FOLDER=""


JBADMIN=${DIRCMD}/jbadmin
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


function copyDeployablesToLanding(){
cd /data/jboss_std/landing/${DOMAIN_NAME}
pwd
rm -rf index.html buildNumbers.txt icargo.ear icargoConfig.zip  EntityViews.zip database.zip postgreSQLSource.zip
echo "deleted previously downloaded files"
_CLASSIC_FOLDER=$CLASSIC_LOCATION_URL"/"$RELEASE_SPECIFIC_FOLDER
echo $_CLASSIC_FOLDER
wget -nv -O $ICARGO_CONFIG $_CLASSIC_FOLDER"/"$ICARGO_CONFIG
wget -nv -O $ICARGO_EAR $_CLASSIC_FOLDER"/"$ICARGO_EAR
}	


function doUpgrade(){
   _VERSION=$(extractLandingVersion | tail -1)
   echo "Version is " $_VERSION
   echo "Version is ----------------------->" $_VERSION
   stopServer
   sleep 30
   echo "SLEEP DONE "
   doDeployment ${_VERSION}
   startServer
  
}


function doDeployment(){
   VER=$1
   ${ICOADMIN} deploy $_DOM $VER
   #data/jboss_std/jicob $ ./icoadmin deploy icocoe1_jdom01 iCO_4.10.9.0.212

}

function stopServer(){
   echo "Stop Server Deployment Triggered"
    cd /data/jboss_std/jicob
	pwd
    ./jbadmin stop icocoe1_jdom01 force
}

function startServer(){
    echo "Start Server Deployment Triggered"
    cd /data/jboss_std/jicob
	pwd
    ./jbadmin start icocoe1_jdom01 force
}



function getTaillogs(){
DOMAIN_DIR=$(getDomainDirectoryForDomain ${_DOM})
_LOG_DIR=${DOMAIN_DIR}"/user_stage/logs/wls"
cd ${_LOG_DIR}
tail -200f ${_ADM}.out
}

function tailInstanceOutLog(){
echo $ROOT_LOG_DIR
local DOMAIN=$1
echo $ROOT_LOG_DIR
local INSTANCES=$(findAllInstancesForDomain $DOMAIN )
echo $INSTANCES
local DOMDIR=$(getDomainDirectoryForDomain ${DOMAIN})
local LOGROOT="/data/jboss_std/logs/"$INSTANCES"/app/"
local LOGFILES
echo "DOMDIR  -> "$DOMDIR
for INST in $INSTANCES
do
   LOGFILES="$LOGFILES $( find ${LOGROOT}/ -type f \( -name "${INST}.log" -o -name "${INST}.out" \) )"
done
echo $LOGFILES
echo "LOGROOT --> "$LOGROOT
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
	'restart')
			${JBADMIN} stop ${DOMAIN_NAME} force
	${JBADMIN} start ${DOMAIN_NAME} tail
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