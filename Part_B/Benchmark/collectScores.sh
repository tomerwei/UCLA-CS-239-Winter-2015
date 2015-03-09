#!/bin/bash

cd closure-compiler

# Run and collect scores

ant clean compile compile-tests

cd ..

reportD=reports/closure-compiler

./CCT/build.sh

./instrument.sh

./run-tests.sh

./passFailReport.sh $reportD > $reportD/passFail.csv

python cctReport.py $reportD/*cct > $reportD/cctCounts.csv

python scoreReport.py method $reportD/passFail.csv $reportD/*cct > $reportD/method-scores.csv

python scoreReport.py stack $reportD/passFail.csv $reportD/*cct > $reportD/stack-scores.csv

cd joda-time
mvn clean 
mvn compile
mvn test
cd ..
reportJT=reports/joda-time

./instrument-jt.sh

./run-jt-tests.sh

./passFailReport.sh $reportJT > $reportJT/passFail.csv

python cctReport.py $reportJT/*cct > $reportJT/cctCounts.csv

python scoreReport.py method $reportJT/passFail.csv $reportJT/*cct > $reportJT/method-scores.csv

python scoreReport.py stack $reportJT/passFail.csv $reportJT/*cct > $reportJT/stack-scores.csv
