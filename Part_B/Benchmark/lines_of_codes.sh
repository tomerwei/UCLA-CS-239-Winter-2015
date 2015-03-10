#!/bin/bash

d=$1/src

for f in $(find $d -name '*\.java')
do
  java -Djava.awt.headless=true -cp locc-4.2.jar csdl.locc.sys.LOCTotal -sizetype javaline -infiles $f
done
