Welcome to AppFuse Light!

==================
 About AppFuse Light
==================
AppFuse Light is a lightweight version of AppFuse (http://appfuse.org). I was
inspired to create it while writing Spring Live (http://springlive.com) and
looking at the struts-blank and webapp-minimal applications that ship with Struts
and Spring, respectively. These "starter" apps were not robust enough for me,
and I wanted something like AppFuse, only simpler. 

Much of the documentation for developing with AppFuse Light can be found in the
QuickStart Chapter from Spring Live at http://springlive.com. Most of the code 
in 1.1+ is from Spring Live. Chapter 11 contains a detailed analysis of the 
different web frameworks and their respective code.

Issues?

* Check the FAQ: http://confluence.sourcebeat.com/display/SPL/FAQ
* Mailing List: http://appfuse.org/display/APF/Mailing+Lists
* Project Home: https://appfuse-light.dev.java.net

==================
 Setup
==================
To run this application, you will need to have PostgreSQL (or MySQL) installed.
PostgreSQL is the default database and you will need to create a "appfuse_light" 
database and "postgres/postgres" user for everything to work properly. 
PostgreSQL version 8.x or MySQL version 4.1.x is recommended. Both have 
graphical installers and easy to use GUI clients.

To change any PostgreSQL settings, see src/main/resources/jdbc.properties. 

If you'd rather use MySQL or another database, perform the following steps:

1. Modify jdbc.properties to have the correct settings for your database.
2. If you're using Hibernate, change the "hibernate.dialect" property in
   src/main/webapp/WEB-INF/applicationContext-hibernate.xml.
3. Modify pom.xml to replace the PostgreSQL driver with your driver.
 
After setting up your database properly, you should be able to run all the tests
using "mvn test". To create your own application, run "ant new". Then cd into
your project and run "mvn jetty:run" to startup the Maven Jetty plugin. You 
should be able to see your project at http://localhost:8080/${app.name}.

If you'd prefer to use Ant rather than Maven, you will need to tell AppFuse Light
where you've installed your server. Installating Tomcat and defining an 
CATALINA_HOME environment variable is the recommended method, but you can
also specify a "server.home" property in build.properties to use another
server. For example:

server.home=$TOOLS_HOME/resin-3.0.16

You can also integrate Jetty with Eclipse for rapid development with no 
deploy cycle using the following instructions:

http://raibledesigns.com/page/rd?entry=edit_java_webapps_redux_jetty

NOTE: These instructions where written before AppFuse Light followed Maven's
standard directory layout. 1.6+ supports the standard directory layout. You
shouldn't need to modify your project any further after generating the Eclipse
files.

After setting up your environment, you can create your application using
the "QuickStart" instructions below, or deploy this one using the "Build
and Test" instructions.

Eclipse:
----------
Download Maven 2.0.4, install it, and add $M2_HOME/bin to your $PATH. From the 
command line, cd into the appfuse-light directory and type "mvn eclipse:eclipse". If
you'd like to download source JARs as well, run:

mvn eclipse:eclipse -DdownloadSources=true

Get a cup of coffee or soda (or even better, a beer!) while you wait for Maven 
to download all the dependencies.

Eclipse needs to know the path to the local maven repository. Therefore the 
classpath variable M2_REPO has to be set. Execute the following command:

mvn -Declipse.workspace=<path-to-eclipse-workspace> eclipse:add-maven-repo 

You can also define the M2_REPO classpath variable inside Eclipse. From the 
menu bar, select Window > Preferences. Select the Java > Build Path > Classpath 
Variables page. Add a new one with a name of M2_REPO and Path of to your local 
Maven repository (/Users/${username}/.m2/repository on OS X and 
C:\Documents and Settings\${username}\.m2\repository on Windows). 

Once the project files have been created, open Eclipse and go to File > New >
Project > Java Project. Click Next and type "appfuse-light" in the Project name box.
Click Finish to begin importing the project. 

To see how to rapidly develop using Eclipse and Jetty, see the following:

http://raibledesigns.com/page/rd?entry=edit_java_webapps_redux_jetty

IDEA:
----------
Download Maven 2.0.4, install it, and add $M2_HOME/bin to your $PATH. From the 
command line, cd into the appfuse-light directory and type "mvn idea:idea". Get a
cup of coffee or soda (or even better, a beer!) while you wait for Maven 
to download all the dependencies.

After opening your project in IDEA, you may need to modify your Project JDK. 

==================
 QuickStart
==================
Run "ant new" and enter your project name when prompted. Spring MVC is the
default web framework. The default view technology is JSP 2.0, but you can use
Velocity or FreeMarker if you'd rather. The installations for these templating
engines is in the "extras" folder.

NOTE: If you experience any issues with the installers mentioned below, try 
running "ant fixcrlf" from the base appfuse-light directory before running
"ant install".

If you'd like to use JSF, Struts, Tapestry or WebWork instead of Spring MVC, 
you can navigate to the the respective directory in the "extras" folder and 
run "ant install". You can also test the installations by running "ant test".

Hibernate is the default persistence framework. If you'd prefer to use iBATIS, 
JDO (JPOX), OJB or Spring JDBC, their installations exist in the "extras" 
folder. You can install them by navigating to their respective directory
and running "ant install". You can test each installation using "ant test".

NOTE: You can use Maven 2 or Ant to build/test/deploy an AppFuse Light-based project.

==================
 Build and Test
==================
1. Make sure you have junit.jar in your $ANT_HOME/lib directory.

2. Download and install Tomcat 5.x or another 2.4 container. If you setup 
   a CATALINA_HOME environment variable, everything should work w/o changes.
   For another container (i.e. Jetty or Resin), you'll need to define a 
   server.home variable in build.properties (or modify build.xml).

   If you're using Tomcat and you want to use Tomcat's Ant Tasks, you'll need
   to use the "tomcat.xml" file - calling it with "ant -f tomcat.xml target".
   For these targets to work, you'll need an "admin" user with a "manager" role
   in $CATALINA_HOME/conf/tomcat-users.xml. The password is defined in 
   build.properties.

3. Run "ant test-all" to verify all tests pass.

4. Run "ant deploy", start your server and view the application at 
   http://localhost:8080/${app.name}
