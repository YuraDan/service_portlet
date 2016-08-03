Spring Service Portlet MVC type edit
==========================

A Liferay  Portlet  project template using Maven.

* Liferay EE 6.2.10.11 (GA1, SP710)
* Java 1.7
* Portlet 2.0
* Spring Framework 4.1.5
* Annotation-based controller configuration


Deploy
------
If you're using Liferay Portal with Tomcat, copy the war to the deploy directory.

```
$ cp target/liferay-grid-portlet.war $LIFERAY_HOME/deploy/
```

Configuration
-------------

Default settings are Liferay EE 6.2.10.11, Java 1.7, Portlet 2.0, and Spring 4.1.  All can be configured in [pom.xml]

```xml
	<properties>
		<liferay.version>6.2.10.11</liferay.version>
		<java-version>1.7</java-version>
		<portlet-api.version>2.0</portlet-api.version>
		<servlet-api.version>2.5</servlet-api.version>
		<jsp-api.version>2.2</jsp-api.version>
		<jstl.version>1.2</jstl.version>
		<org.springframework-version>4.1.5.RELEASE</org.springframework-version>
		<org.aspectj-version>1.8.5</org.aspectj-version>
		<org.slf4j-version>1.7.10</org.slf4j-version>
	</properties>
```

Run the maven archetype generate command.  Follow the prompts to specify the groupId, artifactId, and version for your project.

```bash
$ mvn archetype:generate -DarchetypeCatalog=local
```

