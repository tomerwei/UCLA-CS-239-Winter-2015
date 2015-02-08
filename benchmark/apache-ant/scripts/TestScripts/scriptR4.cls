#!/bin/sh
# The following is an R script type
unset CLASSPATH
CLASSPATH=/nfs/spectre/u5/dohy/Galileo/gal:/nfs/spectre/u5/dohy/Galileo/gal/BCEL:/nfs/spectre/u5/dohy/Galileo/gal/galileo/lib/commons-collections.jar:.:/nfs/spectre/u5/dohy/Prior/apache-ant/source:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/build/classes:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/build/testcases:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/src/testcases:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/src/etc/testcases:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/lib/junit3.8.1.jar:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/lib/xercesImpl.jar:/nfs/spectre/u5/dohy/Prior/apache-ant/source/ant/lib/xml-apis.jar:/nfs/spectre/a4/solaris8/common/j2sdk1_3_1_02/lib/classes.zip
export CLASSPATH

echo ">>>>>>>>running test 1"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.GzipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t1 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 1

echo ">>>>>>>>running test 2"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.GUnzipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t2 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 2

echo ">>>>>>>>running test 3"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AntStructureTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t3 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 3

echo ">>>>>>>>running test 4"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AntTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t4 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 4

echo ">>>>>>>>running test 5"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.AvailableTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t5 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 5

echo ">>>>>>>>running test 6"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CopydirTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t6 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 6

echo ">>>>>>>>running test 7"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CopyfileTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t7 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 7

echo ">>>>>>>>running test 8"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DeleteTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t8 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 8

echo ">>>>>>>>running test 9"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DeltreeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t9 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 9

echo ">>>>>>>>running test 10"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.EchoTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t10 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 10

echo ">>>>>>>>running test 11"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.FailTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t11 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 11

echo ">>>>>>>>running test 12"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.FilterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t12 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 12

echo ">>>>>>>>running test 13"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.GetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t13 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 13

echo ">>>>>>>>running test 14"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.MkdirTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t14 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 14

echo ">>>>>>>>running test 15"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.RenameTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t15 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 15

echo ">>>>>>>>running test 16"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ReplaceTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t16 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 16

echo ">>>>>>>>running test 17"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t17 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 17

echo ">>>>>>>>running test 18"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.TaskdefTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t18 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 18

echo ">>>>>>>>running test 19"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.UnzipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t19 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 19

echo ">>>>>>>>running test 20"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ZipTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t20 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 20

echo ">>>>>>>>running test 21"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.CommandlineJavaTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t21 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 21

echo ">>>>>>>>running test 22"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.CommandlineTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t22 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 22

echo ">>>>>>>>running test 23"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.EnumeratedAttributeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t23 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 23

echo ">>>>>>>>running test 24"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FileSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t24 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 24

echo ">>>>>>>>running test 25"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.PathTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t25 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 25

echo ">>>>>>>>running test 26"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.PatternSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t26 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 26

echo ">>>>>>>>running test 27"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.IntrospectionHelperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t27 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 27

echo ">>>>>>>>running test 28"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.ProjectTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t28 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 28

echo ">>>>>>>>running test 29"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.ExecuteWatchdogTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t29 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 29

echo ">>>>>>>>running test 30"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.PropertyTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t30 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 30

echo ">>>>>>>>running test 31"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.MapperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t31 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 31

echo ">>>>>>>>running test 32"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.DOMElementWriterTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t32 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 32

echo ">>>>>>>>running test 33"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.GlobPatternMapperTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t33 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 33

echo ">>>>>>>>running test 34"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.IncludeTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t34 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 34

echo ">>>>>>>>running test 35"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunnerTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t35 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 35

echo ">>>>>>>>running test 36"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CopyTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t36 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 36

echo ">>>>>>>>running test 37"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.CVSPassTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t37 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 37

echo ">>>>>>>>running test 38"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.DependSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t38 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 38

echo ">>>>>>>>running test 39"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.FixCrLfTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t39 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 39

echo ">>>>>>>>running test 40"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.JarTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t40 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 40

echo ">>>>>>>>running test 41"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.JavaTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t41 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 41

echo ">>>>>>>>running test 42"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.taskdefs.SleepTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t42 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 42

echo ">>>>>>>>running test 43"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.DescriptionTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t43 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 43

echo ">>>>>>>>running test 44"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FileListTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t44 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 44

echo ">>>>>>>>running test 45"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.types.FilterSetTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t45 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 45

echo ">>>>>>>>running test 46"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.util.FileUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t46 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 46

echo ">>>>>>>>running test 47"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.ant.DirectoryScannerTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t47 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 47

echo ">>>>>>>>running test 48"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.AsiExtraFieldTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t48 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 48

echo ">>>>>>>>running test 49"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ExtraFieldUtilsTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t49 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 49

echo ">>>>>>>>running test 50"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ZipEntryTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t50 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 50

echo ">>>>>>>>running test 51"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ZipShortTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t51 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 51

echo ">>>>>>>>running test 52"
$JAVA_HOME/bin/java -mx48m -classpath ${CLASSPATH}:${JAVA_HOME}/lib/classes.zip -Dant.home=bootstrap -Dbuild.tests=build/testcases -Dtests-classpath.value=${CLASSPATH} junit.textui.TestRunner org.apache.tools.zip.ZipLongTest  > /nfs/spectre/u5/dohy/Prior/apache-ant/outputs/t52 2>&1
/nfs/spectre/u5/dohy/Prior/apache-ant/testplans.alt/testscripts/RemoveTime.sh 52
