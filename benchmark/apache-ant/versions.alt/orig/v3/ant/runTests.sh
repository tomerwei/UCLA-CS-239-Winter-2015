#!/usr/local/bin/bash

current=`pwd`
CLASSPATH="$CLASSPATH":${current}/build/classes:${current}/build/testcases:${current}
for i in ${current}/lib/*.jar
do
    CLASSPATH=$CLASSPATH:$i
done
export CLASSPATH
$JAVA_HOME/bin/java -Dbuild.tests=${current}/build/testcases $* AntTestSuite
