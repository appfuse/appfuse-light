To install Tapestry into AppFuse Light, run "ant install" in this directory.  Running
"ant" will show you a list of possible commands. The "test" target can be 
used to create a project and test this installation.

Known Issues:
-------------
1. There's no easy way to get a URL from a @DirectLink to use in an onclick
   handler.  After installing, if you look at web/WEB-INF/pages/home.html,
   you'll see that I'm just using a hidden link and grabbing its "href"
   attribute to use in a button's onclick handler.
   
2. Sending a PageRedirectException (to do GET after POST) doesn't work. This 
   is considered a bug in Tapestry.
   
      http://nagoya.apache.org/bugzilla/show_bug.cgi?id=30076
      
   More information on these issues and my discussions with the Tapestry User
   List can be found at:
      http://thread.gmane.org/gmane.comp.java.tapestry.user/11246
      
3. No unit tests exist for the Page classes.  Hopefully I'll have time to 
   add these in an upcoming release.
   
4. When clicking on column headings, sometimes the following error occurs:
    
      Page recorder for page users is locked after a commit(), but received a 
      change to property sessionState of component users/table.tableView.
      
   It seems to be quite random, esp. since I'm not saving any sessionState 
   (intentionally) on this page.
   
5. When a birthday is null on the user list page, a long stacktrace occurs
   in the cointainer log (because OGNL hits a NPE). The UI renders as it
   should.
