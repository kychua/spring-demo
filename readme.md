# Readme

This project aims to develop a Java web application starting from the most basic, then layering in additional tools and frameworks progressively to help users understand the roles of such tools and frameworks. This project builds on the code in ["How to Install Apache Tomcat 9 (on Windows, Mac OS X, Ubuntu) and Get Started with Java Servlet Programming"](http://www.ntu.edu.sg/home/ehchua/programming/howto/tomcat_howto.html) tutorial by Chua Hock-Chuan, and incorporates code from other tutorials.

## Setup
1. Set up Java project using Maven
   1. Generate project using Maven with archetype maven-archetype-webapp.
   1. Add `java` folder to `src/main`. 
      * Java code are stored in `src/main/java`
      * HTML files are stored in `src/main/webapp`
      * `web.xml` stores mappings from url patterns to Java servlets.
      * Configuration settings are stored in `pom.xml`.

1. Set up database
   1. Install MySQL using installer (take note of root password)
   1. Start MySQL server in `C:\mysql\bin`: `mysqld --console`. Run client (`mysql -u <username> -p`) in other shells.
   1. Use root account to create new user account and grant permissions:
      ```sql
      mysql -u root -p
      create user 'myuser'@'localhost' identified by 'xxxx';
      grant all on *.* to 'myuser'@'localhost';
      quit;
      ```
    1. Use new user account to create database:
       ```sql
       create database if not exists ebookshop;
    
       use ebookshop;

       drop table if exists books;
	   create table books (
	       id int,
		   title varchar(50),
		   author varchar(50),
		   price float,
		   qty int,
		   primary key (id));
		 
   	   insert into books values (1001, 'Java for dummies', 'Tan Ah Teck', 11.11, 11);
	   insert into books values (1002, 'More Java for dummies', 'Tan Ah Teck', 22.22, 22);
	   insert into books values (1003, 'More Java for more dummies', 'Mohammad Ali', 33.33, 33);
   	   insert into books values (1004, 'A Cup of Java', 'Kumar', 44.44, 44);
	   insert into books values (1005, 'A Teaspoon of Java', 'Kevin Jones', 55.55, 55);
	 
	   select * from books;
	   ```
	1. Add SQL to `pom.xml`'s dependencies.
	   ```xml
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>8.0.13</version>
       </dependency>
       ```

1. Set up server
   1. Install Tomcat
   1. Create user account for deploying code to server: add user to tomcat/conf/tomcat-users.xml
      ```xml
	  <role rolename="manager-gui"/>
	  <role rolename="manager-script"/>
	  <user username="admin" password="password" roles="manager-gui,manager-script" />
	  ```
	1. Add server to maven/conf/settings.xml
		```xml
		<server>
			<id>TomcatServer</id>
			<username>admin</username>
			<password>password</password>
		</server>
		```
	1. Add server to `pom.xml` plugins.
	    ```xml
        <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.2</version>
        <configuration>
            <url>http://localhost:8080/manager/text</url>
            <server>TomcatServer</server>
            <path>/${project.build.finalName}</path>
        </configuration>
        </plugin>
	    ```

1. Add Java, HTML and XML code as per tutorial: http://www.ntu.edu.sg/home/ehchua/programming/howto/tomcat_howto.html (note that directory structure, SQL setup and Tomcat setup should be as per instructions above, not according to the tutorial). Files added:
   * `src\main\java`: `HelloServlet.java`, `QueryServlet.java`
   * `src\main\webapp`: `index.html`, `querybook.html`
   * `src\main\webapp\WEB-INF`: `web.xml`

1. Add Spring/Spring Boot
   1. [Add relevant entries](https://spring.io/guides/gs/rest-service/) to `pom.xml`.
      > If you get an error message `No plugin found for prefix 'spring-boot' in the current project and in the plugin groups`, try adding [these entries](https://stackoverflow.com/a/30857865).
   1. Add code that uses Spring, as per [Spring's tutorial](https://spring.io/guides/gs/rest-service/)
      > The `SpringBootApplication` [must not be in the default package](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html,). Instead, [move the application class](https://better-coding.com/solved-spring-boot-failed-to-read-candidate-component-class-caused-by-java-lang-classnotfoundexception-org-springframework-dao-dataaccessexception/) into a package, i.e. `src/main/java/hello/Application.java` is ok but not `src/main/java/Application.java`
   1. [Change the necessary files](https://www.mkyong.com/spring-boot/spring-boot-deploy-war-file-to-tomcat/) to support deploying to Tomcat via a `.war` file.
      > Ignore the instructions for creating `application.properties` as [it is outdated](https://stackoverflow.com/a/48987109).
   1. Create `application.properties` in `src/main/resources` with `server.servlet.contextPath=/myapp` so that the [URL](https://stackoverflow.com/questions/24452072/how-do-i-choose-the-url-for-my-spring-boot-webapp/48987109#48987109) when deploying via spring-boot will be `localhost:8080/myapp/...` (same as deploying via Tomcat) instead of `localhost:8080/...`.
   1. Change code to use Spring/Spring Boot.

## Using Spring/Spring Boot

### Why use Spring

The Spring Framework is a framework that helps you combine different components (e.g. databases, views) easily. This is done via dependency injection. Instead of having to fetch dependencies the code needs as it starts up, Spring lets you use such objects (beans) as if they already exist. 

Spring instantiates, configures and manages the dependencies and supplies them to the code based on the configurations you supply. For example, you may configure Spring to use only one instance of `PetShop`, returning a cached instance for subsequent requests. Or, you may want Spring to return `PetFood` with `amount` of 1kg whenever you request `SomeFood`. 

This is especially useful when you have many components that you want to combine in different ways or when you need to be able to swap components easily (e.g. in different settings/environments). 

(Adapted from [this answer](https://softwareengineering.stackexchange.com/a/92672))

### Spring vs Spring Boot

[Spring Boot](https://github.com/spring-projects/spring-boot) is built on top of Spring, and simplifies the process of creating Spring projects by choosing convention over configuration. It provides sensible defaults, autoconfigurations and other useful features (e.g. monitoring).

### Spring concepts

#### Spring IoC container (application context)/Dependency Injection

Spring implements the Inversion of Control (IoC) principle (or dependency injection) through the IoC container (application context). Objects define (but not fetch) their dependencies. The container then instantiates, configures and assembles those dependencies (beans) using configuration metadata (that you provide in XML, Java annotations or Java code) and injects those dependencies into the code. See [detailed example of DI in Spring Boot](https://stormpath.com/blog/spring-boot-dependency-injection) and [DI examples in Spring](https://docs.spring.io/spring/docs/4.3.1.RELEASE/spring-framework-reference/htmlsingle/#beans-factory-collaborators).

> `ApplicationContext`, `BeanFactory` are interfaces that represent IoC containers. Spring generally [recommends](https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#context-introduction-ctx-vs-beanfactory) using `ApplicationContext` since it has more functionalities than `BeanFactory`. 

#### Beans

Beans are objects managed by the Spring IoC container. They are created using the configuration metadata (e.g. XML bean definitions). 
For example, beans may be declared implicitly using stereotype annotations (one bean automatically created and configured per class), or declared explicitly using `@Bean`, usually in `@Configuration` classes. 

See examples https://therealdanvega.com/blog/2017/05/17/spring-component-vs-bean
https://therealdanvega.com/blog/2017/05/17/spring-component-vs-bean
https://stackoverflow.com/a/10604537

> `@Bean` annotates a method that returns an object to be registered as a bean, with the method's body containing the logic for creating it. It allows you to configure the bean yourself e.g. when you want to use components from external libraries, where you don't have the code to annotate with `@Component`. https://stackoverflow.com/a/40861225

> `@Configuration` denotes a class that provides configuration for beans.


#### Dispatcher Servlet (Spring MVC)

The dispatcher servlet serves as a front controller, processing all incoming requests and delegating tasks to special beans (like HandlerMapping, ViewResolver beans). 

When the dispatcher servlet receives a request, it
1. Uses `HandlerMapping` to look for the appropriate handler (method) and interceptors (for pre- and post-processing). 
1. Invokes the handler via `HandlerAdapter`'s `handle(HttpServletRequest, HttServletResponse, handler)` method. A `ModelAndView` object is returned. 
   1. `handle()` invokes the handler, passing it the request and response as parameters. 
   1. the handler executes the relevant logic and returns a `ModelAndView` object. 
      > If interceptor handlers are used, pre-processing and post-processing are done before/after the handler processes the request. 
1. Processes the `ModelAndView` object, resolving the view if necessary. `View` can be a `String` view name to be resolved using a `ViewResolver`, or a `View` object. The model is a `Map` (keys and associated values). 

The actual method we write in controllers can take in arguments other than request/response. For instance, we can use `@RequestParam` or `Model`. A full list of permitted arguments can be found [here](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-arguments). 

Similarly, we need not return `ModelAndView` objects. We can return a `String` (representing a `View` name), a `Model`, a `@ResponseBody` (JSON instead of HTML output) and [more](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-return-types).

Read [this](https://stackoverflow.com/a/45337829) to see how `@RequestMapping` works.

#### Stereotype annotations

Classes annotated with @Component are automatically detected by Spring during component scanning. Spring auto-configures and creates beans from these classes, with the bean name the same as the class name, except with the first letter in lowercase. 

@Repository, @Service and @Controller are meta-annotations of @Component, and are thus also detected during component scanning. These can be used to denote the roles of components within the application.

* `@Repository`: executes database related operations; catches platform-specific exceptions and rethrows as Spring's unchecked data access exception
* `@Service`: contains business logic and calls methods in repository layer
* `@Controller`: controller; only classes annotated with `@Controller` can use `@RequestMapping`

https://www.baeldung.com/spring-bean-annotations
https://stackoverflow.com/questions/6827752/whats-the-difference-between-component-repository-service-annotations-in

#### `@Autowired`

Asks Spring for an instance of the annotated class that has corresponding bean. (https://stackoverflow.com/a/34174782)


### SQL/JDBC/JPA/ORM/Hibernate/Spring Data JPA

Java data are stored in objects, whereas SQL (or other relational databases) data are stored in tables. The mismatch between the way objects and tables are designed is known as "object relational impedance mismatch". For example,
* Attribute names and types may not match column names and types 
* Objects are shared whereas tables have relationships (e.g. one-to-many) 
* Multiple classes (e.g. subclasses) may be mapped to a single table and vice versa. 

Before JPA, impedance mismatch was handled by translating results from queries to Java objects e.g. JDBC (Java database connectivity). This involves
1. Reading values from objects and setting them as query parameters
1. Converting query results to objects

This approach is difficult to use as queries can be very complex in large applications, and changes to the structure of the database would necessitate significant code rewrites. An alternative approach is Object Relational Mapping (ORM), where we map objects to tables so that interactions with the database are done via objects (e.g. Book.query(author="Kumar")) instead of queries (SELECT * FROM books WHERE author = "Kumar"). (See https://stackoverflow.com/a/1279678 for details/pros and cons)

JPA (Java Persistence API) is a specification (interface) for implementing the ORM approach. The key components in JPA are:
1. Entity Manager: Handles interactions with the database.
2. Java Persistence Query Language (JPQL): Provides ways to write queries to search entities. Unlike SQL queries, JPQL already knows the mappings between entities.
3. Criteria API: Defines a Java-based API to search databases.

Note that JPA is only a specification - it provides guidelines for ORM libraries to follow but does not provide any functionality. For example, it provides annotations like `@Entity` and `@Table`, designed to be used to map objects to tables. However, without an implementation, these annotations will not do anything. In this project, we will use Hibernate. Hibernate's JPA implementation, which follows the JPA's specification, is what provides the actual functionality. (https://stackoverflow.com/a/9881640) Spring Data JPA provides features that simplify the use of Hibernate, such as automatically generating queries through method name conventions and allowing users to define DAO interfaces (select, update, delete etc.) by extending repositories instead of having to write them. (https://stackoverflow.com/a/45568472, https://stackoverflow.com/a/23863416)

(Adapted from https://dzone.com/articles/introduction-to-jpa-using-spring-boot-data-jpa)

### HTML/JSP/Thymeleaf

## Deploy

* Using Spring
  ```
  mvn spring-boot:run
  ```
* Using maven's tomcat plugin:
  ```
  mvn tomcat7:deploy
  mvn tomcat7:undeploy
  mvn tomcat7:redeploy
  ```
* Using maven + tomcat
  1. Generate `.war` file (generated to `target` by default)
     ```
     mvn package
     ```
  1. Copy `.war` file to `C:\tomcat\webapps`
  1. Start/stop tomcat with `tomcat9.exe start`, `tomcat9.exe stop`

## Acknowledgements/Resources

* Project code taken from: http://www.ntu.edu.sg/home/ehchua/programming/howto/tomcat_howto.html
* Using SQL in Java code, JDBC: http://www.ntu.edu.sg/home/ehchua/programming/java/JDBC_Basic.html
* Getting started with MySQL: http://www.ntu.edu.sg/home/ehchua/programming/sql/MySQL_HowTo.html
* Deploying to Tomcat via Maven: https://www.mkyong.com/maven/how-to-deploy-maven-based-war-file-to-tomcat/
* Using Spring Boot for a simple webapp: https://spring.io/guides/gs/rest-service/
* Configuring Spring to deploy to Tomcat via `.war`: https://www.mkyong.com/spring-boot/spring-boot-deploy-war-file-to-tomcat/
* Structuring code for Spring Boot: https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-structuring-your-code.html