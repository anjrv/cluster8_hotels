# Version control for Cluster 8 Hotels

**Initial setup done by:**
* Compile required class files with `javac *.java`
* Prepare database by running the included Setup script:
    - On windows: `java -cp .;sqlite-jdbc.jar Setup`
    - On UNIX: `java -cp .:sqlite-jdbc.jar Setup`

Setup process will create the required database and populate it according to the information within the included schema.sql script.