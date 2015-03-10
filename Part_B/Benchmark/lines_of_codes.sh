#!/bin/bash

d=closure-compiler/src

for f in $(find $d -name '*\.java')
do
  java -cp locc-4.2.jar csdl.locc.sys.LOCTotal -sizetype javaline -infiles $f
done