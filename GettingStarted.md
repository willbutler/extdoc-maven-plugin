Before you can get the plugin, you need to add a repository to your pom:

```
    <pluginRepositories>
        <pluginRepository>
            <id>extdoc-maven-plugin-repo</id>
            <name>extdoc Maven Plugin Repository</name>
            <url>http://extdoc-maven-plugin.googlecode.com/svn/maven2</url>
        </pluginRepository>
    </pluginRepositories>
```

For site report usage:

```
    <reporting>
        <plugins>
            <plugin>
                <groupId>com.butlerhq</groupId>
                <artifactId>extdoc-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <!-- you need either project or source -->
                    <project>path/to/ext.xml</project>
                    <source>
                        <param>path/to/script/*</param>
                    </source>
                     <!-- template required (get sample from extdoc project) -->
                    <template>path/to/template/template.xml</template>
                </configuration>
            </plugin>
        </plugins>
    </reporting>
```

For standalone usage:

```
    <build>
        <plugins>
            <plugin>
                <groupId>com.butlerhq</groupId>
                <artifactId>extdoc-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <configuration>
                    <!-- you need either project or source -->
                    <project>path/to/ext.xml</project>
                    <source>
                        <param>path/to/script/*</param>
                    </source>
                    <!-- output is optional, defaults to target/site/extdoc -->
                    <output>path/to/output/</output>-->
                     <!-- template required (get sample from extdoc project) -->
                    <template>path/to/template/template.xml</template>
                </configuration>
                <!-- if you add an execution, it defaults to the site stage -->
                <executions>
                    <execution>
                        <id>generate extdoc</id>
                        <goals>
                            <goal>extdoc</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```

To generate the documentation:

```
mvn extdoc:extdoc
```

For command line help, run:

```
mvn extdoc:help -Ddetail=true -Dgoal=extdoc
```