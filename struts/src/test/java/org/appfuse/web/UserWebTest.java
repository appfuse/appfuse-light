package org.appfuse.web;

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
        beginAt("/editUser.html");
        assertTitleKeyMatches("userForm.title");
        setTextField("user.username", "araible");
        setTextField("user.password", "iloveprincesses");
        setTextField("user.firstName", "Abbie");
        setTextField("user.lastName", "Raible");
        setTextField("user.email", "kiddo@appfuse.org");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testListUsers() {
        beginAt("/users.html");

        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[]{"Abbie", "Raible"});
    }

    public void testEditUser() {
        beginAt("/editUser.html?id=" + getInsertedUserId());
        assertTextFieldEquals("user.firstName", "Abbie");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/editUser.html?id=" + getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        submit("delete");
        assertTitleKeyMatches("userList.title");
    }

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Abbie User"
     * @return last id in the table
     */
    protected String getInsertedUserId() {
        beginAt("/users.html");
        assertTablePresent("userList");
        assertTextInTable("userList", "Abbie");
        Table table = getTable("userList");
        Cell cell = (Cell) ((Row) table.getRows().get(table.getRowCount()-1)).getCells().get(0);
        return cell.getValue();
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
