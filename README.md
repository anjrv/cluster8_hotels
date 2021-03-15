# Version control for Cluster 8 Hotels

**Required dependencies:**
\\
hoteldeps.jar located in the root of the project contains dependencies for the JDBC driver as well as mail and activation drivers that provide email features. It is required to compile the email engine and is required by both the email engine and query engine at runtime.

**Initial setup done by:**
* Compile required class:
    - On windows: `javac -cp .;hoteldeps.jar hotels\*.java`
    - On UNIX: `javac -cp .:hoteldeps.jar hotels/*.java`
* Prepare database by running the included Setup script:
    - On windows: `java -cp .;hoteldeps.jar hotels\Setup`
    - On UNIX: `java -cp .:hoteldeps.jar hotels/Setup`

Setup process will create the required database and populate it according to the information within the included schema.sql script.

**A short demo class that provides an example of interface connection:**
* Compile required class file with `javac Demo.java`
* Run with:
    - On windows: `java -cp .;hoteldeps.jar Demo`
    - On UNIX: `java -cp .:hoteldeps.jar Demo`