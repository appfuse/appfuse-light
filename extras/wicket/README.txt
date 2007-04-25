To install Wicket into AppFuse Light, run "ant install" in this directory.  Running
"ant" will show you a list of possible commands. The "test" target can be 
used to create a project and test this installation.

Known Issues:
-------------
1. Wicket has many option for creating grids and tables with sorting and paging. The
   method I chose (DataView) seemed like the easiest to learn and use.

2. In order to use SiteMesh with Wicket, I had to turn off the post-after-redirect
   default behavior. This means you should call setRedirect(true) yourself when going 
   to the next page.

3. The visibility of the FeedbackPanel in UserList.java is controlled by the 
   onDeleteUser and onSaveUser methods of UserForm.java.

4. Tests in UserFormTest test haven't been flushed out for all CRUD operations.

5. There are duplicate i18n keys in BasePage.properties and messages.properties. The
   former is used by Wicket, the later by JSTL.

