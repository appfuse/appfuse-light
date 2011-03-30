package org.appfuse.webapp;

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
        setTextField("user.username", "stripesuser");
        setTextField("user.password", "stripespass");
        setTextField("user.firstName", "Spring");
        setTextField("user.lastName", "User");
        setTextField("user.email", "stripes@appfuse.org");
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
        beginAt("/users");
        assertTitleKeyMatches("userList.title");
        clickLinkWithText("Spring");
        assertTextFieldEquals("user.firstName", "Spring");
        submit("save");
        assertTitleKeyMatches("userList.title");
    }

    public void testDeleteUser() {
        beginAt("/users");
        assertTitleKeyMatches("userList.title");
        clickLinkWithText("Spring");
        assertTitleKeyMatches("userForm.title");
        submit("delete");
        assertTitleKeyMatches("userList.title");
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
