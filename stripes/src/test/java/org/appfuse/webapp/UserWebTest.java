package org.appfuse.webapp;

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
        getTestContext().setBaseUrl(
            "http://" + System.getProperty("cargo.host") + ":" + System.getProperty("cargo.port"));
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
        setTextField("user.username", "stripesuser");
        setTextField("user.password", "stripespass");
        setTextField("user.firstName", "Spring");
        setTextField("user.lastName", "User");
        setTextField("user.email", "stripes@appfuse.org");
        clickButton("save");
        assertTitleKeyMatches("userList.title");
    }

    @Test
    public void listUsers() {
        beginAt("/users");
        assertTitleKeyMatches("userList.title");

        // check that table is present
        assertTablePresent("userList");

        //check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[] {"Spring", "User"});
    }

    @Test
    public void editUser() {
        beginAt("/users");
        assertTitleKeyMatches("userList.title");
        clickLinkWithText("Spring");
        assertTitleKeyMatches("userForm.title");
        assertTextFieldEquals("user.firstName", "Spring");
        setTextField("user.password", "stripespass");
        clickButton("save");
        assertTitleKeyMatches("userList.title");
    }

    @After
    public void removeUser() {
        beginAt("/users");
        assertTitleKeyMatches("userList.title");
        clickLinkWithText("Spring");
        assertTitleKeyMatches("userForm.title");
        clickButton("delete");
        assertTitleKeyMatches("userList.title");
    }

    private void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
