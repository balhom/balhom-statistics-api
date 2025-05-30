# Commands

* Project Creation

~~~
mvn io.quarkus.platform:quarkus-maven-plugin:3.19.1:create "-DprojectGroupId=org.balhom" "-DprojectArtifactId=balhom-statistics-api" "-Dextensions=kotlin,rest-jackson"
~~~

* Install Yaml Config Reader

~~~
mvn quarkus:add-extension "-Dextensions=quarkus-config-yaml"
~~~

* Install Oidc

~~~
mvn quarkus:add-extension "-Dextensions=oidc"
~~~

* Install Cassandra Client

~~~
mvn quarkus:add-extension "-Dextensions=com.datastax.oss.quarkus:cassandra-quarkus-client"
~~~
