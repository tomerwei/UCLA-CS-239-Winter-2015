#!/bin/bash

d=closure-compiler/src

v0=v0
v1=v1

for f in $(cd $d/$v0; find src -name '*\.java')
do
  java -cp locc-4.2.jar csdl.locc.sys.LOCDiff -difftype javaline -old $d/$v0/$f -new $d/$v1/$f
done