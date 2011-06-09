package org.appfuse.webapp;

import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.junit.WebTestCase;

import java.util.ResourceBundle;

public class UserWebTest extends WebTestCase {
    private ResourceBundle messages;

    @Override
    protected void setUp() {
        // to get around Obsolete content type encountered: 'text/javascript'.
        setScriptingEnabled(false);
        getTestContext().setBaseUrl("http://localhost:25888");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        
        beginAt("/users");
        assertTitleEquals("Login | AppFuse Light");
        setTextField("j_username", "admin");
        setTextField("j_password", "admin");
        submit("login");
    }

    @Override
    protected void tearDown() {
       gotoPage("/logout");
    }

    public void testLogin() {
       assertTitleKeyMatches("userList.title");
    }

    public void testLogout() {
        clickLinkWithText("Logout");
        assertTitleKeyMatches("index.title");
    }

    public void testWelcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    public void testAddUser() {
        gotoPage("/userform");
        assertTitleKeyMatches("userForm.title");
        setTextField("username", "springuser");
        setTextField("password", "springuser");
        setTextField("firstName", "Spring");
        setTextField("lastName", "User");
        setTextField("email", "springuser@appfuse.org");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testListUsers() {
        gotoPage("/users");
        assertTitleKeyMatches("userList.title");
        
        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[] {"Spring", "User"});
    }

    public void testEditUser() {
        gotoPage("/userform?id=" + getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        assertTextFieldEquals("firstName", "Spring");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        gotoPage("/userform?id=" + getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        submit("delete");
        assertTitleKeyMatches("userList.title");
    }

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Spring User"
     * @return last id in the table
     */
    protected String getInsertedUserId() {
        gotoPage("/users");
        assertTablePresent("userList");
        assertTextInTable("userList", "Spring");
        Table table = getTable("userList");
        // Find row with Spring in it
        for (Object r : table.getRows()) {
            Row row = (Row) r;
            for (Object c : row.getCells()) {
                Cell cell = (Cell) c;
                if (cell.getValue().contains("Spring")) {
                    return ((Cell) row.getCells().get(0)).getValue();
                }
            };
        }
        return "";
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
