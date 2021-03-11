# Version control for Cluster 8 Hotels

**Initial setup done by:**
* Compile required class files with `javac hotels/*.java`
* Prepare database by running the included Setup script:
    - On windows: `java -cp .;sqlite-jdbc.jar hotels/Setup`
    - On UNIX: `java -cp .:sqlite-jdbc.jar hotels/Setup`

Setup process will create the required database and populate it according to the information within the included schema.sql script.

**A short demo class that provides an example of interface connection:**
* Compile required class file with `javac Demo.java`
* Run with:
    - On windows: `java -cp .;sqlite-jdbc.jar Demo`
    - On UNIX: `java -cp .:sqlite-jdbc.jar Demo`