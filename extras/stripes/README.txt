To install Stripes into AppFuse Light, run "ant install" in this directory. Running
"ant" will show you a list of possible commands. The "test" target can be 
used to create a project and test this installation.

Known Issues:
-------------
1. It doesn't seem possible to put <stripes:messages> in a messages.jsp file that's
   included in a SiteMesh decorator. Putting in directly in the page works fine.

2. Tests in UserFormBeanTest test haven't been flushed out for all CRUD operations.
