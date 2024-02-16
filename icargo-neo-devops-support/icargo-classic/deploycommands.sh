#!/bin/sh
#Author: Gokul Kumar M G (A-9627)
#declarations
companyCode="IBS_SPECIFIC"
DB="postgre"
#server="Jboss"
dbSource="postgreSQLSource"
#Stop the server
echo "server start area"
cd /data/jboss_std/jicob
pwd
# COMMENTED OFF
#./jbadmin stop icocoe1_jdom01
#back to deploymentWorkArea  folder
echo "back to deploy area"
cd /opt/jboss-ear-download/
#cd deploymentWorkArea/
pwd
#delete the previous downloads
rm -rf index.html buildNumbers.txt icargo.ear icargoConfig.zip  EntityViews.zip database.zip postgreSQLSource.zip
echo "deleted previously downloaded files"
#wget -qO - 'http://10.246.12.109:8080/execute?domain=ico_alpha_dom&url=http://icargobuild01:8080/ReleaseArea4.10/4.10_Master/iCO_4.10.9.0.196/IBS_SPECIFIC/&action=download'
#get the latest build number
wget -c  http://icargobuild01:8080/ReleaseArea4.10/4.10_Master/iCO_4.10.9.0.207/ | grep -Eo "iCO_4.10.9"
cat index.html | grep  -oP -m 1 'iCO_4.10.9.[a-zA-Z0-9./?=_-]*' > buildNumbers.txt
build_num=$(head -1 buildNumbers.txt)
echo $build_num
#construct the build number specific url and echo the same
buildUrl="http://icargobuild01:8080/ReleaseArea4.10/4.10_Master/"$build_num
echo $buildUrl
echo  $build_num | sed 's/.$//'
#echo ./icoadmin deploy icoivv1_jdom01 $build_num | sed 's/.$//'
#download icargo.ear, icargoConfig.zip, EntityViews.zip
wget -qO -c  $buildUrl$server/$companyCode/icargo.ear
wget -c $buildUrl$server/$companyCode/iCargoConfig.zip
wget -c $buildUrl$server/$companyCode/EntityViews.zip
echo "downloaded icargo.ear iCargoConfig.zip EntityViews.zip"
#download database.zip based on DB
wget -c $buildUrl/$DB/database.zip
echo "downloaded database.zip"
#download DatabaseSource.zip
wget -c $buildUrl/$dbSource.zip
echo "downloaded" $dbSource".zip"
#back up the existing icargo.ear and iCargoConfig.zip
#cd  /data/jboss_std/landing/icoivv1_jdom01/backup
pwd
#rm -rf icargo.ear iCargoConfig.zip
#mv /data/jboss_std/landing/icoivv1_jdom01/icargo.ear .
#mv /data/jboss_std/landing/icoivv1_jdom01/iCargoConfig.zip .
#copy icargo.ear and iCargoConfig.zip to landing folder
#cd /home/a-9627/
#cd deploymentWorkArea/
pwd
#cp -rl icargo.ear /data/jboss_std/landing/icoivv1_jdom01/
#cp -rl iCargoConfig.zip /data/jboss_std/landing/icoivv1_jdom01/
#mv icargo.ear /data/jboss_std/landing/icoivv1_jdom01/
#mv iCargoConfig.zip /data/jboss_std/landing/icoivv1_jdom01/
#Run the deploy command
#cd /data/jboss_std/jicob
pwd
echo "Deploying "$build_num
#./icoadmin deploy icoivv1_jdom01 $build_num | sed 's/.$//'
echo "Deployment Success.............."
