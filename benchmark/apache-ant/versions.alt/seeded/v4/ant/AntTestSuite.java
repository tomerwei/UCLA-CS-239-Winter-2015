import junit.framework.*;

/**
 * To compile this class, you must first set the classpath to point to
 * build/ant/classes, build/ant/testcases, and lib/junit3.8.1.jar
 */
public class AntTestSuite extends TestSuite {
    public static TestSuite suite() {
        TestSuite suite = new TestSuite();
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
	// new tests for v4
        suite.addTestSuite(org.apache.tools.ant.taskdefs.condition.ContainsTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.condition.EqualsTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.optional.depend.DependTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.optional.EchoPropertiesTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.optional.PropertyFileTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.optional.XmlValidateTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.BasenameTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.BUnzip2Test.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.BZip2Test.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ChecksumTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ConcatTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ConditionTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.DirnameTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.DynamicTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ExecuteJavaTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.InitializeClassTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.InputTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.JavacTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.LoadFileTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.LoadPropertiesTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ManifestTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.MoveTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.ParallelTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.RmicTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.TStampTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.TypedefTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.UntarTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.WarTest.class);
        suite.addTestSuite(org.apache.tools.ant.taskdefs.XmlPropertyTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.ContainsSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.DateSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.DependSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.DepthSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.FilenameSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.PresentSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.selectors.SizeSelectorTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.DirSetTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.FlexIntegerTest.class);
        suite.addTestSuite(org.apache.tools.ant.types.XMLCatalogTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.facade.FacadeTaskHelperTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.facade.ImplementationSpecificArgumentTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.CollectionUtilsTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.DateUtilsTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.JavaEnvUtilsTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.PackageNameMapperTest.class);
        suite.addTestSuite(org.apache.tools.ant.util.StringUtilsTest.class);
        suite.addTestSuite(org.apache.tools.ant.AntClassLoaderTest.class);
        suite.addTestSuite(org.apache.tools.ant.ImmutableTest.class);
        suite.addTestSuite(org.apache.tools.ant.PropertyExpansionTest.class);
        return suite;
    }
}
