#!/bin/sh

if [ $# -lt 2 ]; then
    echo "Usage install.sh <directory name> <version number>"
    echo "<directory name> : orig, seeded"
    echo "<version number> : 0, ..., 10"
    exit 1
fi

if [ ! -d ${experiment_root}/apache-ant/versions.alt/$1/v${2} ]; then
    echo "Invalid version number"
    exit 1
fi

if [ -d ${experiment_root}/apache-ant/source/ant ]; then
    rm -rf ${experiment_root}/apache-ant/source/ant
fi

echo cleaning source directory...
rm -rf ${experiment_root}/source/*
echo copying...
cp -rf ${experiment_root}/apache-ant/versions.alt/$1/v${2}/ant ${experiment_root}/apache-ant/source

if [ $2 -eq 0 ]; then
    CLASSPATH=/net/frost/export/u2/dohy/OSU-DIRS/dohy/Galileo/gal:${experiment_root}/apache-ant/source/ant/build/ant/testcases:${experiment_root}/apache-ant/source/ant/build/ant/classes
else
    CLASSPATH=/net/frost/export/u2/dohy/OSU-DIRS/dohy/Galileo/gal:${experiment_root}/apache-ant/source/ant/build/testcases:${experiment_root}/apache-ant/source/ant/build/classes
fi
export CLASSPATH
${experiment_root}/apache-ant/scripts/build.sh
