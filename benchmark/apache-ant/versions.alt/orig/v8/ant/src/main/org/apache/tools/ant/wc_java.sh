#!/bin/sh
#if test $# -ne 2
#then echo "Usage: $0 <version number> <instrument option>"
#        exit 1
#fi
#
num=0
new_num=0
find . -name "*.java" > dirList
while read LINE
do
	new_num=`cat $LINE | wc -l`
	num=`expr ${num} + ${new_num}`
	echo $num
done < dirList
echo $num
