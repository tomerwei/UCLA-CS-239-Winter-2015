# UCLA-CS-239-Winter-2015
CS 239, Class Project Part B, UCLA, Winter 2015

Part B Project: Automated Debugging of Program Stack Traces

Requirements

1. Include at least two different data sets (two projects) and compare and contrast your findings

2. Ensure adequate tests (e.g., more than 20 tests). The following projects are reported to have large number of JUnit tests over 2000 tests: JFreeChart, Closure Compiler, Commons Math, Joda Time, Commons Lang. 

3. Ensure adequate failing tests (e.g., in the order of tens). If you cannot find failing tests, you may seed faults or you can also transplant the tests from the new version and apply to the old version to reconstruct failing tests. If you seed faults, please describe the criteria of seeding faults. 

4. Include the details of results and the characteristics of the data set---the number tests, the program size in terms of LOC, the number of passing vs. failing tests, the coverage of tests, the size of changes, etc. 

5. Describe the process of calculating metrics. For the suspicious score, since the instrumentation is done at the method stack level, you must describe how you computed suspicious score at what levels. For example if you calculate the suspicious score at a method call count level, please describe the process. If you describe the suspicious score at a method level based by aggregating the frequency of the top most stack, please describe the process of how you did it, using examples. You must include an example of how you computed suspicious score metrics.       

6. Discuss the ranks of fault seeded locations in terms of individual suspicious scores. Clarity a response variable and predictor variables for linear regression. Include linear regression results and interpretation - R^2, F statistics, p-value, etc. Include interpretation of your findings and include a summary of findings.

Presentation:
http://goo.gl/njPvUg
