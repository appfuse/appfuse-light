package org.appfuse.web;

import net.sourceforge.jwebunit.WebTestCase;

public class UserWebTest extends WebTestCase {

    public UserWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080/appfuse-light");
        getTestContext().setResourceBundleName("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testWelcomePage() {
        beginAt("/");
        assertTitleEqualsKey("index.title");
    }

    public void testAddUser() {
        beginAt("/userform.html");
        assertTitleEqualsKey("userForm.title");
        setFormElement("firstName", "Spring");
        setFormElement("lastName", "User");
        submit("save");
        assertTitleEqualsKey("userList.title");
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
        assertTitleEqualsKey("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/userform.html?id=" + getInsertedUserId());
        assertTitleEqualsKey("userForm.title");
        submit("delete");
        assertTitleEqualsKey("userList.title");
    }

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Spring User"
     */
    protected String getInsertedUserId() {
        beginAt("/users.html");
        assertTablePresent("userList");
        assertTextInTable("userList", "Spring");
        String[][] tableCellValues =
                getDialog().getSparseTableBySummaryOrId("userList");
        return tableCellValues[tableCellValues.length-1][0];
    }
}
