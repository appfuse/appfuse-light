package org.appfuse.webapp.pages;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserListTest extends BasePageTestCase {

    @Test
    public void testListUsers() {
        doc = tester.renderPage("UserList");
        assertNotNull(doc.getElementById("userList"));
        assertTrue(doc.getElementById("userList").find("tbody/tr").getChildren().size() >= 1);
    }
}
