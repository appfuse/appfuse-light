To install JSF into AppFuse Light, run "ant install" in this directory.  Running
"ant" will show you a list of possible commands.  The "test" target can be 
used to create a project and test this installation.

Known Issues:
-------------
1. There is quite a workaround in userList.jsp to get the displaytag to work
   with JSF.
2. JSF UIs are difficult to test since it's all JavaScript and js.jar (Rhino)
   isn't that good at interpreting valid JavaScript.  It often says there's 
   errors when there aren't any visible in the modern browsers.
3. No unit tests exist for the managed beans.  Hopefully I'll have time to 
   add these in an upcoming release.
