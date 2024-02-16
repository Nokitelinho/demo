#
# This script is to be run as the IBS-USER_ID / password.
# provide the password whenever prompted.
#
# After successful creation of the monitoring environments,
# change the --icargo.http.host=10.246.12.109 --icargo.http.port=8000
# in the 3 "start.sh" files of folders /opt/ebl-masters , /opt/ebl-booking , /opt/ebl-security
# with the actual ip and port of the classic icargo running in the respective environment.

#
# Modify the FROM_MACHINE_IP : from where the monitoring applications are to be copied
# LOCAL_MACHINE_IP is the machine ip, in which those applications are to be installed. (it will be the newly created box)

#
# **************************** ***************** ************************
# paramters for which value to be provided


USER_ID=icargoadm
PASSWORD="icargoADM@123"
#FROM_MACHINE_IP=10.246.12.17
FROM_MACHINE_IP=10.246.12.112
LOCAL_MACHINE_IP=10.246.12.45

##################################################################### 
#	parameters for internal user. Change only if it is necessary.

ZIP_FILE_NAME=file2copy.tar
TEMP_FOLDER=/tmp/setups

FILE_NAME="starter.py"

declare -a SERVICES=( ebl-booking.service ebl-masters.service 
			ebl-security.service kafka.service 
			elasticsearch.service kibana.service 
			kafdrop.service txp-web.service	)
						
						
# pip3  install paramiko
#service_files=("ebl-booking.service",,ebl-masters.service,ebl-security.service,kafka.service,elasticsearch.service,kibana.service,kafdrop.service,txp-web.service{

compressAndCopy(){

   echo -e "Zipping files in the remote machine... This may take several minutes \n"
   echo -e "Please enter password \n\n"


   #ssh $FROM_MACHINE_IP "cd /opt; sudo tar -cf /tmp/$ZIP_FILE_NAME ebl-booking ebl-masters ebl-security elasticsearch-7.9.1 kafka_2.12-2.4.1 amazon-corretto-8.275.01.1-linux-x64 kafdrop neo-txprobe-aggregator neob kibana-7.9.1-linux-x86_64"

   ssh -q  $FROM_MACHINE_IP  	"cd /opt; 
			echo \"zipping ebl-booking, ebl-masters, ebl-security \";
			sudo tar -cf /tmp/$ZIP_FILE_NAME ebl-booking ebl-masters ebl-security ; 
			echo \"zipping  elasticsearch-7.9.1 kafka_2.12-2.4.1 amazon-corretto-8.275.01.1-linux-x64 \" ; 
			sudo tar --append --file=/tmp/$ZIP_FILE_NAME  elasticsearch-7.9.1 kafka_2.12-2.4.1 amazon-corretto-8.275.01.1-linux-x64;
			echo \"zipping kafdrop neo-txprobe-aggregator neob kibana-7.9.1-linux-x86_64 \" ;
			sudo tar --append --file=/tmp/$ZIP_FILE_NAME kafdrop neo-txprobe-aggregator neob kibana-7.9.1-linux-x86_64"

   echo -e  "Completed zipping directories \n\n"
   echo -e "Enter password for copying to $LOCAL_MACHINE_IP \n\n"
   scp  $FROM_MACHINE_IP:/tmp/$ZIP_FILE_NAME $TEMP_FOLDER/$ZIP_FILE_NAME 

   echo -e "Copied zip file to $LOCAL_MACHINE_IP $TEMP_FOLDER \n\n"

   echo -e "Copying service files from $FROM_MACHINE_IP to $LOCAL_MACHINE_IP $TEMP_FOLDER \n\n"
   echo -e "Please enter pasword "

   scp $FROM_MACHINE_IP:/etc/systemd/system/*.service $TEMP_FOLDER

}

writePythonFile() {
	echo "import os" > $FILE_NAME
	echo "import paramiko" >> $FILE_NAME

	echo 'server="'$FROM_MACHINE_IP'"' >> $FILE_NAME
	echo 'username="'$USER_ID'"' >> $FILE_NAME
	echo 'password="'$PASSWORD'"' >> $FILE_NAME
	echo 'ssh = paramiko.SSHClient()' >> $FILE_NAME
	echo 'ssh.load_host_keys(os.path.expanduser(os.path.join("~", ".ssh", "known_hosts"))) ' >> $FILE_NAME
	echo "ssh.connect(server, username=username, password=password) " >> $FILE_NAME
	
	echo 'service_files = ["ebl-booking.service", "ebl-masters.service", "ebl-security.service", "kafka.service", "elasticsearch.service", "kibana.service", "kafdrop.service", "txp-web.service"]'>> $FILE_NAME
	
	echo 'cmd = "cd /opt; tar -cf /tmp/'$ZIP_FILE_NAME' ebl-booking ebl-masters ebl-security elasticsearch-7.9.1 kafka_2.12-2.4.1 amazon-corretto-8.275.01.1-linux-x64 kafdrop neo-txprobe-aggregator neob kibana-7.9.1-linux-x86_64; "' >> $FILE_NAME
	
	echo 'channel = ssh.invoke_shell()' >> $FILE_NAME
	echo 'print("Zipping application folders, to copy ")' >> $FILE_NAME
	echo 'stdin,stdout,stderr = ssh.exec_command(cmd)' >> $FILE_NAME
 	echo 'stdout = stdout.readlines()' >> $FILE_NAME
	echo 'print("Completed zipping files")' >> $FILE_NAME
	
	echo "sftp = ssh.open_sftp() " >> $FILE_NAME
	echo 'print("Copying zipped file to local")' >> $FILE_NAME
	echo 'ret_val=sftp.get("/tmp/'$ZIP_FILE_NAME'", "'$TEMP_FOLDER'/'$ZIP_FILE_NAME'") ' >> $FILE_NAME
	echo 'print("Completed copying zip file to '$TEMP_FOLDER'")' >> $FILE_NAME
	
	echo 'print("Copying system service files to local")' >> $FILE_NAME
	echo 'for file in service_files:' >> $FILE_NAME
	echo '	file_remote = "/etc/systemd/system/" + file'>> $FILE_NAME
	echo '	file_local = "'$TEMP_FOLDER'/" + file'>> $FILE_NAME
	echo '	sftp.get(file_remote, file_local) ' >> $FILE_NAME
	echo 'print("Completed copying system files to '$TEMP_FOLDER'")' >> $FILE_NAME

	echo "sftp.close() " >> $FILE_NAME
	echo "ssh.close() " >> $FILE_NAME
	
}

removeJunkFiles(){
	sudo rm -f *.gz *.log log.txt
	sudo rm -rf work logs
}
 

 
# main functionality starts here 
sudo sh -c 'useradd icargoadm' 
sudo sh -c 'useradd intfuser' 
mkdir $TEMP_FOLDER
compressAndCopy
sudo mkdir /data
sudo sh -c 'chown -R '$USER_ID':'$USER_ID' /data'
#writePythonFile
#python3 $FILE_NAME
cd $TEMP_FOLDER
sudo sh -c 'chown -R '$USER_ID':'$USER_ID' *'
echo "Unzipping the copied zip file "$ZIP_FILE_NAME
sudo sh -c 'tar -xf '$ZIP_FILE_NAME' -C /opt'
cd /opt
echo "configuring ebl-booking directory"
cd ebl-book*
removeJunkFiles
sudo sed -i 's/'$FROM_MACHINE_IP'/'$LOCAL_MACHINE_IP'/g' start.sh
echo "	edited start.sh with "$LOCAL_MACHINE_IP


echo "configuring ebl-masters directory"
cd ../ebl-mast*
removeJunkFiles
echo "configuring ebl-security directory"
cd ../ebl-secu*
removeJunkFiles

echo "configuring neob directory"
cd ../neob
sudo sed -i 's/'$FROM_MACHINE_IP'/'$LOCAL_MACHINE_IP'/g' start-kafdrop.sh
echo "	edited start-kafdrop.sh with "$LOCAL_MACHINE_IP
sudo sed -i 's/'$FROM_MACHINE_IP'/'$LOCAL_MACHINE_IP'/g' etc/txpstack.conf
echo "	edited txpstack.conf with "$LOCAL_MACHINE_IP



echo "configuring kafka directory"
cd ../kafk*
cd logs
sudo find . -name "*" -delete
cd ..
#sudo rm -rf logs/*
cd config
sudo sed -i 's/'$FROM_MACHINE_IP'/'$LOCAL_MACHINE_IP'/g' server.properties
echo "	edited server.properties with "$LOCAL_MACHINE_IP

_CONF_FILE=/etc/sysctl.conf
echo "modifying "$_CONF_FILE", to add vm.max_map_count"
if grep -q "vm.max_map_count" $_CONF_FILE; then
	echo "	vm.max_map_count exists in the file"
	cat $_CONF_FILE | grep "vm.max_map_count"
else
	sudo sh -c ' echo "vm.max_map_count=262144" >> '$_CONF_FILE
	echo "	vm.max_map_count added to "$_CONF_FILE
fi

_CONF_FILE=/etc/security/limits.conf
echo "modifying "$_CONF_FILE", to add soft memlock etc"
if grep -q "soft memlock" $_CONF_FILE; then
	echo "	soft memlock exists in the file"
	cat $_CONF_FILE | grep "soft memlock"
else
	sudo sh -c ' echo "# Added by iCargo Team" >> '$_CONF_FILE
	sudo sh -c ' echo "* soft memlock unlimited" >> '$_CONF_FILE
	sudo sh -c ' echo "* hard memlock unlimited" >> '$_CONF_FILE
	sudo sh -c ' echo "* soft nofile 65535" >> '$_CONF_FILE
	sudo sh -c ' echo "* hard nofile 65535" >> '$_CONF_FILE	
	echo "	soft memlock added to "$_CONF_FILE
fi

sudo yum remove redis

echo 'Completed modifying config files'
echo 'Installing redis'
sudo sh -c 'amazon-linux-extras install redis4.0'

_CONF_FILE=/etc/redis.conf
cd $TEMP_FOLDER
sudo sh -c 'cp '$_CONF_FILE' '$TEMP_FOLDER
sudo sh -c 'chown -R '$USER_ID':'$USER_ID' redis.conf'
pwd
out1=$(sudo grep "protected-mode" redis.conf | grep -w  "yes")
if [[ "$out1" == "protected-mode yes" ]]
then
	sudo sed -i 's/bind 127.0.0.1/bind 127.0.0.1 '$LOCAL_MACHINE_IP'/g' redis.conf
	sudo sed -i 's/protected-mode yes/protected-mode no/g' redis.conf
	sudo sh -c ' cp -f redis.conf /etc/'
	echo "	modified the redis config file for 'protected-mode no' and 'bind IP'"
else
	echo "	redis is already configured for this machine"
	cat $_CONF_FILE | grep $LOCAL_MACHINE_IP
	cat $_CONF_FILE | grep "bind 127.0.0.1"
	cat $_CONF_FILE | grep "protected-mode"
fi


echo 'enabling services'
sleep 5
sudo sh -c 'systemctl enable redis'
sudo sh -c 'systemctl start redis'
sudo sh -c 'systemctl status redis'
cd $TEMP_FOLDER
sudo sh -c 'cp *.service /etc/systemd/system/'
for svc in "${SERVICES[@]}"
#for svc in *.service
do
	sudo sh -c 'systemctl enable '$svc
	sudo sh -c 'systemctl start '$svc
	sleep 8
	sudo sh -c 'systemctl status '$svc
done

sudo jps -l



