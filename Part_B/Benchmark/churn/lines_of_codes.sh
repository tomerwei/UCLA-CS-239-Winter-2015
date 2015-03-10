#!/bin/bash
d=closure-compiler
for f in $(cd $d; find src -name '*\.java')
do
  java -cp locc-4.2.jar csdl.locc.sys.LOCTotal -sizetype javaline -infiles $d/$f
done
