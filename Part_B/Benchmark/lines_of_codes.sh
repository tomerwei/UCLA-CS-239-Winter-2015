#!/bin/bash

d=closure-compiler/src

v1=v1

for f in $(cd $d/$v1; find src -name '*\.java')
do
  java -cp locc-4.2.jar csdl.locc.sys.LOCTotal -sizetype javaline -infiles $d/$v1/$f
done