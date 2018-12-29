# PO_PttkGot

## Adding libraries in IntelliJ
* SHIFT+CTRL+ALT+S
* At the left panel "Modules"
* At the right panel "Dependencies"
* At the right side "+"
* "JARs or directories..."
* Select wanted \*.jar file


## Defining dependencies in Maven
* In pom.xml file define repository folder inside &lt;repositories\&gt; &lt;/repositories&gt; tags e.g. 
```
    <repository>
        <id>my-local-repo</id>
        <url>file://${basedir}/libs</url>
    </repository>

```
* Add dependency like:
```
    <dependency>
        <groupId>jstl</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
```
inside &lt;dependencies&gt; &lt;/dependencies&gt; tags.

