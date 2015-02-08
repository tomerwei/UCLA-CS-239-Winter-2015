#!/usr/local/bin/bash

CLASSPATH="$CLASSPATH":.
export CLASSPATH
cd ${experiment_root}/apache-ant/source/ant

echo build...
if [ -f build_unix.sh ]; then
    build_unix.sh compiletests
    exit 0
fi

build.sh compile-tests
