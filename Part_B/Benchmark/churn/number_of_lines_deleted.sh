#!/bin/bash

v0=closure-compiler-pre
v1=closure-compiler

for f in $(cd closure-compiler-pre;find src -name '*\.java')
do
  java -cp locc-4.2.jar csdl.locc.sys.LOCDiff -difftype javaline -old $v1/$f -new $v0/$f
done