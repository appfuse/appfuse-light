package org.appfuse.webapp;

import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.junit.WebTestCase;

import java.util.ResourceBundle;

public class UserWebTest extends WebTestCase {
    private ResourceBundle messages;
    
    public void setUp() {
        setScriptingEnabled(false);
        getTestContext().setBaseUrl("http://localhost:25888");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
    }
    
    public void testWelcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    public void testAddUser() {
        beginAt("/userform");
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
        beginAt("/users");

        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[] {"Spring", "User"});
    }

    public void testEditUser() {
        beginAt("/userform?id=" + getInsertedUserId());
        assertTextFieldEquals("firstName", "Spring");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/userform?id=" + getInsertedUserId());
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
        beginAt("/users");
        assertTablePresent("userList");
        assertTextInTable("userList", "Spring");
        Table table = getTable("userList");
        Cell cell = (Cell) ((Row) table.getRows().get(table.getRowCount()-1)).getCells().get(0);
        return cell.getValue();
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
