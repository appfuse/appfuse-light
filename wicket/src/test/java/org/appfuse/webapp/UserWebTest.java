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
        setTextField("username", "suser");
        setTextField("password", "spass");
        setTextField("firstName", "Spring");
        setTextField("lastName", "User");
        setTextField("email", "suser@appfuse.org");
        clickElementByXPath("//button[@name=\"save\"]");
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
        assertTextFieldEquals("firstName", "Spring");
        clickElementByXPath("//button[@name=\"save\"]");
        assertTitleKeyMatches("userList.title");
    }

    @After
    public void removeUser() {
        beginAt("/users");
        assertTitleKeyMatches("userList.title");
        clickLinkWithText("Spring");
        assertTitleKeyMatches("userForm.title");
        clickElementByXPath("//button[@name=\"delete\"]");
        assertTitleKeyMatches("userList.title");
    }

    private void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
