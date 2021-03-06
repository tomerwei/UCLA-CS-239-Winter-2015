#!/bin/bash

export CCT=$(readlink -f "$(dirname $0)/../CCT/")
#export LIB=$(readlink -f "$(dirname $0)/../../lib/")
#export CLASSPATH=$LIB/guava.jar:$LIB/gson.jar:$LIB/jsr305.jar:$LIB/protobuf-java.jar:$CCT:$CCT/asm-5.0.3.jar:.
export CLASSPATH=$CCT:$CCT/asm-5.0.3.jar:.

echo $CLASSPATH

for f in $(find target/classes -name '*class')
do
  mkdir -p instrumented/$(dirname $f)
  java -cp $CLASSPATH Instrument $f instrumented/$f
done	
