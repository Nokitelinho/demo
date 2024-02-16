#!/bin/bash

# @ Author : Akshay S Kumar (A-10228) 

#Configure below variables specific to machine/Impl.

BUILDVER="$1"
COMCOD=DHL
DOMNAME=icoivv1_jdom141
HOMDIR=/app/AutoDeployDHL
JBSTD=/volume/data/jboss_std
DBHOST=10.246.13.29
DATABASE=ico_classic_dhl_stg
USERNAME=ico_classic_dhl_stg_usr
PASSWORD=ico_classic_dhl_stg_usr
PATHTOWEB=$JBOSSSTD/home/app/$DOMNAME/icargo.ear/icargo-web.war/WEB-INF/web.xml
JICOB=$JBOSSSTD/jicob
DBASE=$HOMDIR/dbase
DELTALINK="http://icargodev37:8080/ReleaseArea/GIT_DatabaseDelta/Delta_"
RELEASE1="http://192.168.49.28:8080/ReleaseArea4.10/4.10_Master/"
RELEASEFULL=$RELEASE1$BUILDVER"/Jboss/"$COMCOD"_SPECIFIC"
LANDING=/data/jboss_std/landing/$DOMNAME
CURRVER=$(grep "iCO" $PATHTOWEB | grep -o -P '(?<=version.).*(?=</)')
DELTAFULL=$DELTALINK$CURRVER"_"$BUILDVER"_"$COMCOD"/postgre/database.zip"
HOST=$DELTAFULL
STATUS=200
TIMEOUT=600

echo "Current Build:  $CURRVER"
echo "Build to deploy: $BUILDVER"

cd $DBASE

echo removing Old DB files..
rm -rf *.zip
rm -rf *.txt
rm -rf database
rm -rf postgreSQLSource
rm -rf EntityViews
rm -rf postgresRunner.sh


DELTAJOB="http://192.168.49.28:8080/jenkins/job/DatabaseDeltaScripts_4.10GIT/buildWithParameters?FROM_VERSION="$CURRVER"&TO_VERSION="$BUILDVER"&COMPANY_CODE="$COMCOD

curl -X POST $DELTAJOB

cd $LANDING

echo removing old build files..
rm -rf icargo.ear
rm -rf iCargoConfig.zip

#########-------- Downloading Build files --------#########

echo downloading new build files..
wget "$RELEASEFULL/icargo.ear" -q
wget "$RELEASEFULL/iCargoConfig.zip" -q

echo Waiting to download database scripts..

sleep 1m

HOST=$HOST STATUS=$STATUS timeout --foreground -s TERM $TIMEOUT bash -c \
    'while [[ ${STATUS_RECEIVED} != ${STATUS} ]];\
        do STATUS_RECEIVED=$(curl -s -o /dev/null -L -w ''%{http_code}'' ${HOST}) && \
        echo "received status: $STATUS_RECEIVED" && \
        sleep 15;\
    done;
    echo success with status: $STATUS_RECEIVED'

cd $DBASE

echo downloading database files..
wget "$DELTAFULL" 
wget $RELEASE1$BUILDVER"/postgreSQLSource.zip" -q
wget "$RELEASEFULL/EntityViews.zip" -q

sleep 10

#########-------- Database parsing and Execution --------#########


cd $DBASE

./DBShellUtil.sh $DBHOST $DATABASE $USERNAME $PASSWORD $COMCOD

./postgresRunner.sh

#########-------- Deployment and Restart --------#########

echo Stopping Server...!
cd $JICOB
./jbadmin stop $DOMNAME

sleep 30s

echo Deploying Version $BUILDVER
cd $JICOB
./icoadmin deploy $DOMNAME $BUILDVER

sleep 1m

cd $JICOB
echo Starting server..
./jbadmin start $DOMNAME

echo Server $DOMNAME now deployed with $BUILDVER. Server will be up in few minutes..


