# Readme

This project is a modification of ["How to Install Apache Tomcat 9 (on Windows, Mac OS X, Ubuntu) and Get Started with Java Servlet Programming"](http://www.ntu.edu.sg/home/ehchua/programming/howto/tomcat_howto.html) tutorial by Chua Hock-Chuan, to include use of Maven.

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

## Deploy

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
