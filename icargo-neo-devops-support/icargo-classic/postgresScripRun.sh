#Author : Gokul Kumar M G(A-9627)
: ${1?Please append Company Code to extract}
: ${2?Please append datebase user}
: ${3?Please append database password}
: ${4?Please append database host ip with port in format ip:port}
: ${5?Please append database name}
echo "Executing Postgres Script"
set -e
unzip -o -d database database.zip $1/*
unzip -o -d postgreSQLSource postgreSQLSource.zip
unzip -o -d EntityViews EntityViews.zip
sudo rm -rf List.txt
rm -rf driver.sh
pushd postgreSQLSource/Menu || { echo 'No such directory path exist' ; exit 1; }
#find . -type d -not -name $1 -delete
pwd
find . -type d -maxdepth 1 -mindepth 1 -not -name $1 -exec rm -rf {} \;
popd
find $PWD -type f -name "*.sql" | sort >> List.txt
rm -rf allDirectories.txt newList1.txt newList_tmp.txt newList2.txt
pushd postgreSQLSource/procedures/src/
for file in ./** ./**/* ./**/**/* ./**/**/**/*; do
    if [ -d "$file" ]; then
       echo $file>>../../../allDirectories.txt
    fi
done
popd
grep "pkg_" allDirectories.txt|sed 's/.*pkg/pkg/' > packageDirectories.txt
pwd
for word in $(cat packageDirectories.txt); do 
  grep -v $word.sql List.txt > newList1.txt; 
done
for word in $(cat packageDirectories.txt); do 
  grep -v $word.sql List.txt > newList1.txt; 
done
grep pkg_shr_fil_gen.sql List.txt >> newList1.txt
grep stk_cnt_dep.sql List.txt >> newList1.txt

grep  "_types.sql" List.txt >> newList1.txt
cp List.txt newList2.txt
for i in $(cat newList1.txt); do
   grep -v $i newList2.txt >newList_tmp.txt
   mv newList_tmp.txt newList2.txt
done
while IFS= read -r line;do
        echo "\\echo PROMPT "$line" starts at: >> dbOutput.log" >> driver.sh
        echo "psql postgres://$2:$3@$4/$5 -c select now >> dbOutput.log" >> driver.sh
        echo "psql postgres://$2:$3@$4/$5 -f $line -o out.txt 2>>dbOutput.log">>driver.sh
        echo "\\echo PROMPT "$line" ends at: >> dbOutput.log" >> driver.sh
        echo "psql postgres://$2:$3@$4/$5 -c select now >> dbOutput.log" >> driver.sh
done < "newList1.txt"
echo "Executing Postgres Script 13"
while IFS= read -r line;do
        echo "\\echo PROMPT "$line" starts at: >> dbOutput.log" >> driver.sh
        echo "psql postgres://$2:$3@$4/$5 -c select now >> dbOutput.log" >> driver.sh
        echo "psql postgres://$2:$3@$4/$5 -f $line -o out.txt 2>>dbOutput.log">>driver.sh
        echo "\\echo PROMPT "$line" ends at: >> dbOutput.log" >> driver.sh
        echo "psql postgres://$2:$3@$4/$5 -c select now >> dbOutput.log" >> driver.sh
done < "newList2.txt"