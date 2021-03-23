# Version control for Cluster 8 Hotels

**Required dependencies:**
\
hoteldeps.jar located in the root of the project contains dependencies for the JDBC driver as well as mail and activation drivers that provide email features. It is required to compile the email engine and is required by both the email engine and query engine at runtime.

**Initial setup done by:**
* Compile required class:
    - On Windows: `javac -cp ".;hoteldeps.jar" hotels/*.java`
    - On UNIX/Mac: `javac -cp .:hoteldeps.jar hotels/*.java`
* Prepare database by running the included Setup script:
    - On Windows: `java -cp ".;hoteldeps.jar" hotels/Setup`
    - On UNIX/Mac: `java -cp .:hoteldeps.jar hotels/Setup`

Setup process will create the required database and populate it according to the information within the included schema.sql script.

**Test cases can be run:**
* Compile tests:
    - On Windows: `javac -cp ".;testing.jar;hoteldeps.jar" RunTests.java`
    - On UNIX/Mac: `javac -cp .:testing.jar:hoteldeps.jar RunTests.java`
* Run test cases:
    - On Windows: `java -cp ".;testing.jar;hoteldeps.jar" org.junit.runner.JUnitCore RunTests`
    - On UNIX/Mac: `java -cp .:testing.jar:hoteldeps.jar org.junit.runner.JUnitCore RunTests`

**A short demo class that provides an example of interface connection:**
* Compile required class file with `javac Demo.java`
* Run with:
    - On Windows: `java -cp ".;hoteldeps.jar" Demo`
    - On UNIX/Mac: `java -cp .:hoteldeps.jar Demo`
