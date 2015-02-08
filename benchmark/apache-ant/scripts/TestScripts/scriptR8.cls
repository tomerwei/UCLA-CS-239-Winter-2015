#!/bin/sh
# The following is an R script type
unset CLASSPATH
CLASSPATH=/nfs/spectre/u5/dohy/Galileo/gal:/nfs/spectre/u5/dohy/Galileo/gal/BCEL:/nfs/spectre/u5/dohy/Galileo/gal/galileo/lib/commons-collections.jar:.:/nfs/spectre/u5/dohy/Prior/apache-ant/source:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/build/classes:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/build/testcases:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/src/testcases:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/src/etc/testcases:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/lib/junit3.8.1.jar:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/lib/xercesImpl.jar:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/lib/xml-apis.jar:/nfs/spectre/a4/solaris8/common/j2sdk1_3_1_02/lib/classes.zip
export CLASSPATH

echo ">>>>>>>>running test 1"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.GzipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t1 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 1

echo ">>>>>>>>running test 2"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.GUnzipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t2 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 2

echo ">>>>>>>>running test 3"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AntStructureTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t3 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 3

echo ">>>>>>>>running test 4"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AntTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t4 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 4

echo ">>>>>>>>running test 5"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AvailableTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t5 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 5

echo ">>>>>>>>running test 6"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CopydirTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t6 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 6

echo ">>>>>>>>running test 7"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CopyfileTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t7 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 7

echo ">>>>>>>>running test 8"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DeleteTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t8 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 8

echo ">>>>>>>>running test 9"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DeltreeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t9 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 9

echo ">>>>>>>>running test 10"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.EchoTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t10 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 10

echo ">>>>>>>>running test 11"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.FailTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t11 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 11

echo ">>>>>>>>running test 12"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.FilterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t12 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 12

echo ">>>>>>>>running test 13"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.GetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t13 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 13

echo ">>>>>>>>running test 14"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.MkdirTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t14 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 14

echo ">>>>>>>>running test 15"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.RenameTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t15 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 15

echo ">>>>>>>>running test 16"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ReplaceTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t16 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 16

echo ">>>>>>>>running test 17"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t17 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 17

echo ">>>>>>>>running test 18"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TaskdefTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t18 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 18

echo ">>>>>>>>running test 19"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.UnzipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t19 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 19

echo ">>>>>>>>running test 20"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ZipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t20 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 20

echo ">>>>>>>>running test 21"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.CommandlineJavaTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t21 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 21

echo ">>>>>>>>running test 22"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.CommandlineTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t22 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 22

echo ">>>>>>>>running test 23"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.EnumeratedAttributeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t23 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 23

echo ">>>>>>>>running test 24"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FileSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t24 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 24

echo ">>>>>>>>running test 25"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.PathTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t25 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 25

echo ">>>>>>>>running test 26"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.PatternSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t26 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 26

echo ">>>>>>>>running test 27"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.IntrospectionHelperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t27 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 27

echo ">>>>>>>>running test 28"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.ProjectTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t28 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 28

echo ">>>>>>>>running test 29"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ExecuteWatchdogTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t29 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 29

echo ">>>>>>>>running test 30"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.PropertyTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t30 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 30

echo ">>>>>>>>running test 31"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.MapperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t31 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 31

echo ">>>>>>>>running test 32"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.DOMElementWriterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t32 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 32

echo ">>>>>>>>running test 33"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.GlobPatternMapperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t33 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 33

echo ">>>>>>>>running test 34"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.IncludeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t34 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 34

echo ">>>>>>>>running test 35"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunnerTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t35 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 35

echo ">>>>>>>>running test 36"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CopyTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t36 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 36

echo ">>>>>>>>running test 37"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CVSPassTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t37 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 37

echo ">>>>>>>>running test 38"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DependSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t38 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 38

echo ">>>>>>>>running test 39"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.FixCrLfTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t39 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 39

echo ">>>>>>>>running test 40"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.JarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t40 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 40

echo ">>>>>>>>running test 41"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.JavaTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t41 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 41

echo ">>>>>>>>running test 42"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.SleepTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t42 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 42

echo ">>>>>>>>running test 43"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.DescriptionTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t43 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 43

echo ">>>>>>>>running test 44"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FileListTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t44 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 44

echo ">>>>>>>>running test 45"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FilterSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t45 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 45

echo ">>>>>>>>running test 46"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.FileUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t46 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 46

echo ">>>>>>>>running test 47"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.DirectoryScannerTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t47 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 47

echo ">>>>>>>>running test 48"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.AsiExtraFieldTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t48 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 48

echo ">>>>>>>>running test 49"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ExtraFieldUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t49 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 49

echo ">>>>>>>>running test 50"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ZipEntryTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t50 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 50

echo ">>>>>>>>running test 51"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ZipShortTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t51 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 51

echo ">>>>>>>>running test 52"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ZipLongTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t52 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 52

echo ">>>>>>>>running test 53"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.condition.ContainsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t53 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 53

echo ">>>>>>>>running test 54"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.condition.EqualsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t54 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 54

echo ">>>>>>>>running test 55"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.depend.DependTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t55 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 55

echo ">>>>>>>>running test 56"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.EchoPropertiesTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t56 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 56

echo ">>>>>>>>running test 57"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.PropertyFileTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t57 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 57

echo ">>>>>>>>running test 58"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.XmlValidateTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t58 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 58

echo ">>>>>>>>running test 59"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.BasenameTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t59 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 59

echo ">>>>>>>>running test 60"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.BUnzip2Test  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t60 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 60

echo ">>>>>>>>running test 61"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.BZip2Test  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t61 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 61

echo ">>>>>>>>running test 62"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ChecksumTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t62 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 62

echo ">>>>>>>>running test 63"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ConcatTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t63 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 63

echo ">>>>>>>>running test 64"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ConditionTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t64 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 64

echo ">>>>>>>>running test 65"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DirnameTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t65 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 65

echo ">>>>>>>>running test 66"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DynamicTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t66 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 66

echo ">>>>>>>>running test 67"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ExecuteJavaTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t67 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 67

echo ">>>>>>>>running test 68"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.InitializeClassTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t68 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 68

echo ">>>>>>>>running test 69"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.InputTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t69 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 69

echo ">>>>>>>>running test 70"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.JavacTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t70 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 70

echo ">>>>>>>>running test 71"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.LoadFileTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t71 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 71

echo ">>>>>>>>running test 72"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.LoadPropertiesTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t72 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 72

echo ">>>>>>>>running test 73"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ManifestTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t73 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 73

echo ">>>>>>>>running test 74"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.MoveTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t74 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 74

echo ">>>>>>>>running test 75"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ParallelTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t75 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 75

echo ">>>>>>>>running test 76"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.RmicTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t76 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 76

echo ">>>>>>>>running test 77"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TStampTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t77 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 77

echo ">>>>>>>>running test 78"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TypedefTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t78 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 78

echo ">>>>>>>>running test 79"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.UntarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t79 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 79

echo ">>>>>>>>running test 80"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.WarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t80 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 80

echo ">>>>>>>>running test 81"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.XmlPropertyTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t81 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 81

echo ">>>>>>>>running test 82"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.ContainsSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t82 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 82

echo ">>>>>>>>running test 83"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.DateSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t83 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 83

echo ">>>>>>>>running test 84"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.DependSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t84 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 84

echo ">>>>>>>>running test 85"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.DepthSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t85 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 85

echo ">>>>>>>>running test 86"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.FilenameSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t86 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 86

echo ">>>>>>>>running test 87"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.PresentSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t87 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 87

echo ">>>>>>>>running test 88"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.SizeSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t88 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 88

echo ">>>>>>>>running test 89"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.DirSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t89 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 89

echo ">>>>>>>>running test 90"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FlexIntegerTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t90 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 90

echo ">>>>>>>>running test 91"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.XMLCatalogTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t91 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 91

echo ">>>>>>>>running test 92"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.facade.FacadeTaskHelperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t92 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 92

echo ">>>>>>>>running test 93"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.facade.ImplementationSpecificArgumentTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t93 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 93

echo ">>>>>>>>running test 94"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.CollectionUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t94 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 94

echo ">>>>>>>>running test 95"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.DateUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t95 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 95

echo ">>>>>>>>running test 96"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.JavaEnvUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t96 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 96

echo ">>>>>>>>running test 97"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.PackageNameMapperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t97 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 97

echo ">>>>>>>>running test 98"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.StringUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t98 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 98

echo ">>>>>>>>running test 99"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.AntClassLoaderTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t99 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 99

echo ">>>>>>>>running test 100"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.ImmutableTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t100 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 100

echo ">>>>>>>>running test 101"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.PropertyExpansionTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t101 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 101

echo ">>>>>>>>running test 102"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.LineContainsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t102 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 102

echo ">>>>>>>>running test 103"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t103 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 103

echo ">>>>>>>>running test 104"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.JAXPUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t104 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 104

echo ">>>>>>>>running test 105"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.TaskContainerTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t105 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 105

echo ">>>>>>>>running test 106"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AntLikeTasksAtTopLevelTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t106 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 106

echo ">>>>>>>>running test 107"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AntlibTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t107 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 107

echo ">>>>>>>>running test 108"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CallTargetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t108 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 108

echo ">>>>>>>>running test 109"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DefaultExcludesTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t109 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 109

echo ">>>>>>>>running test 110"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ExecTaskTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t110 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 110

echo ">>>>>>>>running test 111"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ImportTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t111 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 111

echo ">>>>>>>>running test 112"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.MacroDefTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t112 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 112

echo ">>>>>>>>running test 113"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.MultiMapTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t113 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 113

echo ">>>>>>>>running test 114"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.PreSetDefTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t114 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 114

echo ">>>>>>>>running test 115"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.SignJarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t115 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 115

echo ">>>>>>>>running test 116"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.SubAntTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t116 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 116

echo ">>>>>>>>running test 117"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TouchTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t117 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 117

echo ">>>>>>>>running test 118"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TypeAdapterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t118 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 118

echo ">>>>>>>>running test 119"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.XmlnsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t119 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 119

echo ">>>>>>>>running test 120"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.condition.IsReferenceTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t120 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 120

echo ">>>>>>>>running test 121"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.email.EmailAddressTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t121 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 121

echo ">>>>>>>>running test 122"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.email.EmailTaskTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t122 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 122

echo ">>>>>>>>running test 123"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.i18n.TranslateTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t123 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 123

echo ">>>>>>>>running test 124"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.unix.SymlinkTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t124 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 124

echo ">>>>>>>>running test 125"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.AddTypeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t125 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 125

echo ">>>>>>>>running test 126"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.AssertionsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t126 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 126

echo ">>>>>>>>running test 127"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.PermissionsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t127 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 127

echo ">>>>>>>>running test 128"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.PolyTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t128 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 128

echo ">>>>>>>>running test 129"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.ZipFileSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t129 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 129

echo ">>>>>>>>running test 130"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.ModifiedSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t130 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 130

echo ">>>>>>>>running test 131"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.selectors.TypeSelectorTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t131 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 131

echo ">>>>>>>>running test 132"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.LazyFileOutputStreamTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t132 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 132

echo ">>>>>>>>running test 133"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.LoaderUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t133 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 133

echo ">>>>>>>>running test 134"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.ReferencesTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t134 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 134

echo ">>>>>>>>running test 135"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.ResourceUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t135 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 135

echo ">>>>>>>>running test 136"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.UnPackageNameMapperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t136 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 136

echo ">>>>>>>>running test 137"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.mail.MailMessageTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t137 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 137

echo ">>>>>>>>running test 138"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.tar.TarEntryTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t138 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 138

echo ">>>>>>>>running test 139"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.tar.TarRoundTripTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t139 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 139

echo ">>>>>>>>running test 140"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.CaseTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t140 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 140

echo ">>>>>>>>running test 141"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.LoaderRefTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t141 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 141

echo ">>>>>>>>running test 142"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.TopLevelTaskTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t142 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 142

echo ">>>>>>>>running test 143"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.ConcatFilterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t143 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 143

echo ">>>>>>>>running test 144"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.DynamicFilterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t144 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 144

echo ">>>>>>>>running test 145"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.EscapeUnicodeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t145 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 145

echo ">>>>>>>>running test 146"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.HeadTailTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t146 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 146

echo ">>>>>>>>running test 147"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.NoNewLineTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t147 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 147

echo ">>>>>>>>running test 148"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.ReplaceTokensTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t148 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 148

echo ">>>>>>>>running test 149"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.StripJavaCommentsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t149 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 149

echo ">>>>>>>>running test 150"
$JAVA_HOME/bin/java -mx256m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.filters.TokenFilterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t150 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 150
