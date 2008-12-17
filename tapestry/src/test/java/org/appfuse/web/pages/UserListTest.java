package org.appfuse.web.pages;

import org.apache.tapestry5.dom.Element;
import org.junit.Test;

public class UserListTest extends BasePageTester {

    @Test
    public void testListUsers() {
        doc = tester.renderPage("UserList");
        assertNotNull(doc.getElementById("userList"));
        assertTrue(doc.getElementById("userList").find("tbody/tr").getChildren().size() >= 1);
    }
}
