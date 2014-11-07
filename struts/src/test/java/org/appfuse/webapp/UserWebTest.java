package org.appfuse.webapp;

import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Cell;
import org.junit.Before;
import org.junit.Test;

import java.util.ResourceBundle;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

public class UserWebTest {
    private ResourceBundle messages;

    @Before
    public void setUp() {
        setScriptingEnabled(false);
        getTestContext().setBaseUrl("http://localhost:25888");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
    }

    @Test
    public void testWelcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    @Test
    public void testAddUser() {
        beginAt("/editUser");
        assertTitleKeyMatches("userForm.title");
        setTextField("user.username", "araible");
        setTextField("user.password", "iloveprincesses");
        setTextField("user.firstName", "Abbie");
        setTextField("user.lastName", "Raible");
        setTextField("user.email", "kiddo@appfuse.org");
        clickButton("save");
        assertTitleKeyMatches("userList.title");
    }

    @Test
    public void testListUsers() {
        beginAt("/users");

        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[]{"Abbie", "Raible"});
    }

    @Test
    public void testEditUser() {
        beginAt("/editUser?id=" + getInsertedUserId());
        assertTextFieldEquals("user.firstName", "Abbie");
        clickButton("save");
        assertTitleKeyMatches("userList.title");
    }

    @Test
    public void testDeleteUser() {
        beginAt("/editUser?id=" + getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        clickButton("delete");
        assertTitleKeyMatches("userList.title");
    }

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Abbie User"
     *
     * @return last id in the table
     */
    protected String getInsertedUserId() {
        beginAt("/users");
        assertTablePresent("userList");
        assertTextInTable("userList", "Abbie");
        Table table = getTable("userList");
        // Find row with Abbie in it
        for (Object r : table.getRows()) {
            Row row = (Row) r;
            for (Object c : row.getCells()) {
                Cell cell = (Cell) c;
                if (cell.getValue().contains("Abbie")) {
                    return ((Cell) row.getCells().get(0)).getValue();
                }
            }
        }
        return "";
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
