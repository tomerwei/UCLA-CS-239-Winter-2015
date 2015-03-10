#!/bin/bash

v0=$1-pre
v1=$1

for f in $(cd $v0;find src -name '*\.java')
do
  java -Djava.awt.headless=true -cp locc-4.2.jar csdl.locc.sys.LOCDiff -difftype javaline -old $v1/$f -new $v0/$f
done
