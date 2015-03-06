#!/bin/bash

javac TestInstrumented.java
java TestInstrumented >/dev/null 2>trace.txt
python CCT.py < trace.txt | sort | uniq -c 

