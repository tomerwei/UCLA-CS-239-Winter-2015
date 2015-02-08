#!/usr/local/bin/bash

current=`pwd`
CLASSPATH="$CLASSPATH":${current}/build/ant/classes:${current}/build/ant/testcases:${current}
for i in ${current}/lib/*.jar
do
    CLASSPATH=$CLASSPATH:$i
done

export CLASSPATH

$JAVA_HOME/bin/java $* AntTestSuite
