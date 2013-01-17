package org.appfuse.webapp;

import net.sourceforge.jwebunit.html.Cell;
import net.sourceforge.jwebunit.html.Row;
import net.sourceforge.jwebunit.html.Table;
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
        setTextField("username", "tapestry");
        setTextField("password", "howard");
        setTextField("firstName", "Tapestry");
        setTextField("lastName", "User");
        setTextField("email", "tapestry@appfuse.org");
        clickButtonWithText("Save");
        assertTitleKeyMatches("userList.title");
    }

    public void testListUsers() {
        beginAt("/userlist");
        assertTitleKeyMatches("userList.title");

        // check that table is present
        assertTablePresent("userList");

        // check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[]{"Tapestry", "User"});
    }

    public void testEditUser() {
        beginAt("/userlist");
        assertTitleKeyMatches("userList.title");
        clickLinkWithExactText(getInsertedUserId());
        assertTextFieldEquals("firstName", "Tapestry");
        clickButtonWithText("Save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/userlist");
        assertTitleKeyMatches("userList.title");
        clickLinkWithExactText(getInsertedUserId());
        assertTitleKeyMatches("userForm.title");
        submit("delete");
        assertTitleKeyMatches("userList.title");
    }

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
    public String getInsertedUserId() {
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
