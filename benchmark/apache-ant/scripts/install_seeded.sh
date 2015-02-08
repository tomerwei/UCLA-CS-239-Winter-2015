#!/usr/local/bin/bash

if [ $# -lt 1 ]; then
    echo "Must give version number to install"
    exit 1
fi

if [ ! -d ${experiment_root}/apache-ant/versions.alt/seeded/v${1} ]; then
    echo "Invalid version number"
    exit 1
fi

if [ -d ${experiment_root}/apache-ant/source/ant ]; then
    rm -rf ${experiment_root}/apache-ant/source/ant
fi

rm -rf ${experiment_root}/source/*
echo copying...
cp -rf ${experiment_root}/apache-ant/versions.alt/seeded/v${1}/ant ${experiment_root}/apache-ant/source
current_dir=`pwd`
cp EqualizeLineNumbers*.class ${experiment_root}/apache-ant/source/.
cd ${experiment_root}/apache-ant/source
# turn off previous fault
find ./ -name "*.cpp" >  __tmpfile
while read LINE
do
        java EqualizeLineNumbers $LINE 0 `echo $LINE | sed "s/\.cpp/\.java/"`
done < __tmpfile
rm __tmpfile

if [ $1 -eq 0 ]; then
    CLASSPATH=$Galileo/gal/BCEL:$Galileo/gal:$Galileo/gal/galileo/lib/commons-collections.jar:${experiment_root}/apache-ant/source/ant/build/ant/testcases:${experiment_root}/apache-ant/source/ant/build/ant/classes
else
    CLASSPATH=$Galileo/gal/BCEL:$Galileo/gal:$Galileo/gal/galileo/lib/commons-collections.jar:${experiment_root}/apache-ant/source/ant/build/testcases:${experiment_root}/apache-ant/source/ant/build/classes
fi
export CLASSPATH

${experiment_root}/apache-ant/scripts/build.sh
