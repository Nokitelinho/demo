#!/bin/sh

TYP="_types.sql"

DBHOST=$1
DATABASE=$2
USERNAME=$3
PASSWORD=$4
COMCOD=$5
PORT=5432

sudo rm -rf packageList.txt typeList.txt otherList.txt FinalList.txt postgresRunner.sh postgreSQLSource errorlog.txt

unzip -d postgreSQLSource postgreSQLSource.zip 
unzip -d EntityViews EntityViews.zip 
unzip -d database database.zip $COMCOD/*

rm -rf postgreSQLSource/Menu

sudo mv database postgreSQLSource
sudo mv EntityViews postgreSQLSource

find $PWD -type f -name "*.sql" | sort >> List.txt


echo "#!/bin/sh" >> postgresRunner.sh
echo HOST=$DBHOST >> postgresRunner.sh
echo DATABASE=$DATABASE >> postgresRunner.sh
echo USERNAME=$USERNAME >> postgresRunner.sh
echo "export PGPASSWORD=$PASSWORD" >> postgresRunner.sh
echo "export PORT=$PORT" >> postgresRunner.sh

cat List.txt | while read line

do

res=$(echo $line | grep -o /pkg_ | wc -l)

if [[ $line == *"$TYP"*  ]];
then
  echo $line >> typeList.txt

elif [[ $res == 2 ]];
then
  echo $line >> packageList.txt
  
else
  echo $line >> otherList.txt
fi

done

cat packageList.txt typeList.txt otherList.txt >> FinalList.txt

cat FinalList.txt | while read line

do

echo 'psql -h $HOST -U $USERNAME -p $PORT -d $DATABASE -f' "\"$line\" " '>>  errorlog.txt 2>&1 ' >> postgresRunner.sh

done

sudo chmod +x postgresRunner.sh
