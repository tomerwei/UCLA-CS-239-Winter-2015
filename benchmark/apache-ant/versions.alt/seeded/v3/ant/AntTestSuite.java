import junit.framework.*;

/**
 * To compile this class, you must first set the classpath to point to
 * build/ant/classes, build/ant/testcases, and lib/junit3.7.jar
 */
public class AntTestSuite extends TestSuite {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
        // JUnit 3.8.1 preferred method of building the comprehensive test suite
        suite.addTestSuite(org.apache.tools.ant.taskdefs.GzipTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.GUnzipTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.AntStructureTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.AntTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.AvailableTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.CopydirTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.CopyfileTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.DeleteTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.DeltreeTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.EchoTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.FailTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.FilterTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.GetTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.MkdirTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.RenameTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ReplaceTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.TarTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.TaskdefTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.UnzipTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ZipTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.CommandlineJavaTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.CommandlineTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.EnumeratedAttributeTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.FileSetTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.PathTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.PatternSetTest.class);
        suite.addTestSuite(org.apache.tools.ant.IntrospectionHelperTest.class);
        suite.addTestSuite(org.apache.tools.ant.ProjectTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ExecuteWatchdogTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.PropertyTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.MapperTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.DOMElementWriterTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.GlobPatternMapperTest.class);
        suite.addTestSuite(org.apache.tools.ant.IncludeTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunnerTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.CopyTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.CVSPassTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.DependSetTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.FixCrLfTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.JarTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.JavaTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.SleepTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.DescriptionTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.FileListTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.FilterSetTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.FileUtilsTest.class);
        suite.addTestSuite(org.apache.tools.ant.DirectoryScannerTest.class);
        suite.addTestSuite(org.apache.tools.zip.AsiExtraFieldTest.class);
        suite.addTestSuite(org.apache.tools.zip.ExtraFieldUtilsTest.class);
        suite.addTestSuite(org.apache.tools.zip.ZipEntryTest.class);
        suite.addTestSuite(org.apache.tools.zip.ZipShortTest.class);
        suite.addTestSuite(org.apache.tools.zip.ZipLongTest.class);
        return suite;
    }
}