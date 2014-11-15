package org.appfuse.webapp;

import net.sourceforge.jwebunit.html.Table;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Cell;
import org.junit.After;
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
    public void welcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    @Before
    public void addUser() {
        beginAt("/userform");
        assertTitleKeyMatches("userForm.title");
        setTextField("username", "tapestry");
        setTextField("password", "howard");
        setTextField("firstName", "Tapestry");
        setTextField("lastName", "User");
        setTextField("email", "tapestry@appfuse.org");
        clickButton("save");
        assertTitleKeyMatches("userList.title");
    }

    @Test
    public void listUsers() {
        beginAt("/userlist");
        assertTitleKeyMatches("userList.title");

        // check that table is present
        assertTablePresent("userList");

        // check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[]{"Tapestry", "User"});
    }

    @Test
    public void editUser() {
        beginAt("/userlist");
        assertTitleKeyMatches("userList.title");
        clickLinkWithExactText(getInsertedUserId());
        assertTextFieldEquals("firstName", "Tapestry");
        clickButton("save");
        assertTitleKeyMatches("userList.title");
    }

    @After
    public void removeUser() {
        beginAt("/userlist");
        assertTitleKeyMatches("userList.title");
        clickLinkWithExactText(getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        submit("delete");
        assertTitleKeyMatches("userList.title");
    }

    @Test
    public void testCancel() {
        beginAt("/userform");
        assertTitleKeyMatches("userForm.title");
        submit("cancel_0");
        assertTitleKeyMatches("userList.title");
    }

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Spring User"
     *
     * @return last id in the table
     */
    private String getInsertedUserId() {
        beginAt("/userlist");
        assertTablePresent("userList");
        assertTextInTable("userList", "Tapestry");
        Table table = getTable("userList");
        // Find row with Spring in it
        for (Object r : table.getRows()) {
            Row row = (Row) r;
            for (Object c : row.getCells()) {
                Cell cell = (Cell) c;
                if (cell.getValue().contains("Tapestry")) {
                    return (row.getCells().get(0)).getValue();
                }
            }
        }
        return "";
    }

    private void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
