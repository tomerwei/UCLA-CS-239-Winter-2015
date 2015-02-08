#!/bin/sh

CLASSPATH="$CLASSPATH":.:./lib/junit3.8.1.jar
export CLASSPATH
echo $CLASSPATH
JAVA_HOME=/usr/java1.4.2/j2sdk1.4.2
export JAVA_HOME

# Set install directory here
REALANTHOME=$HOME/subjects/apache-ant/ant_build

#REALANTHOME=$ANT_HOME
ANT_HOME=.
export ANT_HOME

if test ! -f lib/ant.jar -o  ! -x bin/ant -o ! -x bin/antRun ; then
  ./bootstrap.sh
fi    

if [ "$REALANTHOME" != "" ] ; then
  ANT_INSTALL="-Dant.install $REALANTHOME"
fi

bin/ant $ANT_INSTALL $*
javac AntTestSuite.java
