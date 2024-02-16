#!/bin/bash
#set -x

##############################################################
#                                                            #
# iCargo classic database sql running script				 #
# @Author : A-9721@ibsplc.com                                #
# Date : 25-Jun-2021                                         #
#                                                            #
##############################################################

export ORACLE_HOME=/opt/oraInstClient_21_1/bin
export LD_LIBRARY_PATH=$ORACLE_HOME
export SQLPATH=$ORACLE_HOME
export LIBPATH=$ORACLE_HOME
export SHLIB_PATH=$ORACLE_HOME

ACTION=$1
USER=$2
PASSWORD=$3
HOST_IP_PORT=$4
DB=$5
FILE_FOLDER=$6

ERROR_FILE="script_error.log"
INFO_FILE="script_info.log"
LOCAL_SQL_FILE="_local_sql.sql"

LINE="*************************************************"



function executeScriptFile(){
	SQL_FILE=$1
	printf "Executing %s " "${SQL_FILE}"
	LEN=$(( 80 - $( expr length  "${SQL_FILE}" ) ))
	FMT="%${LEN}s "

	echo "SET SQLBLANKLINES ON;" > ${LOCAL_SQL_FILE}
	echo "SET DEFINE OFF;" >> ${LOCAL_SQL_FILE}
	echo "SET SQLPREFIX OFF;" >> ${LOCAL_SQL_FILE}
	echo "@${SQL_FILE}" >> ${LOCAL_SQL_FILE}
	result=$( $ORACLE_HOME/sqlplus -s " $USER/$PASSWORD@$HOST_IP_PORT/$DB" < "${LOCAL_SQL_FILE}" )
	if [[ "$result"  == *"ERROR"* ]];
	then
		printf "$FMT [  FAILED  ]\n"
		echo -e "$(date) Error occured -> $1 \n $result" >> $ERROR_FILE
		echo -e "${LINE}  \n $result"  >&1
	else
		echo -e "$(date) Execution sucesss -> $1 \n $result"  >> $INFO_FILE
		printf "$FMT [  OK  ]\n"
	fi

}

function compileDBSchema() {
	SCHEMA=$1
	echo "Compiling DB Schema ${SCHEMA}. This may take several minutes."
	echo "EXEC DBMS_UTILITY.compile_schema(schema => '${SCHEMA}');" > ${LOCAL_SQL_FILE}
	result=$( $ORACLE_HOME/sqlplus -s " $USER/$PASSWORD@$HOST_IP_PORT/$DB" < "${LOCAL_SQL_FILE}" )
	if [[ "$result"  == *"ERROR"* ]];
	then
		echo "Error occured while compiling the schema"
		echo "${result}"
	else
		echo "DB Schema Compilation successfull."
	fi
}

function usage() {
	echo "Usage..."
}


# main execution starts
if [[ ${ACTION} == "" ]] ; then
   usage
   exit 1
fi

case ${ACTION} in
	'file')
		executeScriptFile "${FILE_FOLDER}"
		;;
	'folder')
		# FILES=$(find "${FILE_FOLDER}" -name "*.sql")
		# for FIL in $FILES 
		# to solve the problem that files with space in the name
		find "${FILE_FOLDER}" -iname  "*.sql" |  while read FIL
		do        
			executeScriptFile "${FIL}"
		done
		exit
		;;
	'clearlogs')
		rm -rf ${ERROR_FILE}
		rm -rf ${INFO_FILE}
		exit
		;;
	'compile')
		compileDBSchema "${USER}"
		exit
		;;
esac





