To install Tapestry into AppFuse Light, run "ant install" in this directory.  Running
"ant" will show you a list of possible commands. The "test" target can be 
used to create a project and test this installation.

Known Issues:
-------------
1. There's no easy way to get a URL from a @DirectLink to use in an onclick
   handler.  After installing, if you look at web/WEB-INF/pages/home.html,
   you'll see that I'm just using a hidden link and grabbing its "href"
   attribute to use in a button's onclick handler.
   
2. When a birthday is null on the user list page, a long stacktrace occurs
   in the container log (because OGNL hits a NPE). The UI renders as it
   should.
