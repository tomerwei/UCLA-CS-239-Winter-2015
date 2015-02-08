import junit.framework.*;

/**
 * To compile this class, you must first set the classpath to point to
 * build/classes, build/testcases, and lib/junit3.7.jar
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
	//new tests for v1
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ExecuteWatchdogTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.PropertyTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.MapperTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.DOMElementWriterTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.GlobPatternMapperTest.class);
        suite.addTestSuite(org.apache.tools.ant.IncludeTest.class);
        return suite;
    }
}
