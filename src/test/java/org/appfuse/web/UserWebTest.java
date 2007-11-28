package org.appfuse.web;

import net.sourceforge.jwebunit.WebTestCase;

import java.util.ResourceBundle;

public class UserWebTest extends WebTestCase {
    private ResourceBundle messages;

    public UserWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:25888");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testWelcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    public void testAddUser() {
        beginAt("/userform.html");
        assertTitleKeyMatches("userForm.title");
        setFormElement("firstName", "Spring");
        setFormElement("lastName", "User");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testListUsers() {
        beginAt("/users.html");

        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList",
                new String[]{"Spring", "User"});
    }

    public void testEditUser() {
        beginAt("/userform.html?id=" + getInsertedUserId());
        assertFormElementEquals("firstName", "Spring");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/userform.html?id=" + getInsertedUserId());
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
        beginAt("/users.html");
        assertTablePresent("userList");
        assertTextInTable("userList", "Spring");
        String[][] tableCellValues =
                getDialog().getSparseTableBySummaryOrId("userList");
        return tableCellValues[tableCellValues.length-1][0];
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name")); 
    }
}
