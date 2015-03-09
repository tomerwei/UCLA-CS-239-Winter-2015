#!/bin/bash

CCT=$(pwd)
CCT="$CCT/CCT"

export CLASSPATH=$CCT:$CCT/asm-5.0.3.jar:.

echo $CLASSPATH

cd closure-compiler/build/
mkdir -p instrumented

cd classes

for f in $(find com -name '*class')
do
  mkdir -p ../instrumented/$(dirname $f)
  java Instrument $f ../instrumented/$f
done

