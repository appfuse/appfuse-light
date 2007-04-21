To install Acegi Security into AppFuse Light, run "ant install" from this directory.
Running "ant" will show you a list of possible commands. The "test" 
target can be used to create a project and test this installation.

Features installed by this package:

1. /WEB-INF/security.xml file that protects *.html. Has an InMemoryDaoImpl
   defined with users/passwords defined in XML. Can be modified to talk
   to a database or LDAP. Contact users@appfuse.dev.java.net or consult
   Acegi Security's documentation at http://acegisecurity.org/reference.html.
  
2. Login screen with Remember Me functionality.

3. Logout functionality via Logout link in decorators/default.jsp.*

4. UserManager.removeUser() only available to admins, even if delete
   button is displayed on userForm.jsp.*
   
5. 403 error page to display user-friendly message when access denied.

* Parsing JSPs to add Logout link and hide the delete button for the "user"
role may only work with Spring MVC. See build.xml in this directory to see
what you'll need to add to these files.