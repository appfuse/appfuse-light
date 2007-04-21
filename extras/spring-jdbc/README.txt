To replace Hibernate with Spring JDBC, run "ant install" in this directory.  
Running "ant" will show you a list of possible commands.  The "test" target 
can be used to create a project and test this installation.

NOTE: Since Spring JDBC can't auto-create database tables like Hibernate can, a 
      "createdb" target is added to build.xml.  This is called before any
      tests are run.  It deletes and re-creates the table structure each time.
