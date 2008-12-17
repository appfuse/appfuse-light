package org.appfuse.web.pages;

import org.apache.tapestry5.test.PageTester;
import org.appfuse.web.services.IntegrationTestModule;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class UserIntegrationTest extends UserFormTest {

    @Before
    @Override
    public void before() {
        String appPackage = "org.appfuse.web";
        String appName = "app";
        tester = new PageTester(appPackage, appName, "src/main/webapp", IntegrationTestModule.class);
        testType = INTEGRATION;
        fieldValues = new HashMap<String, String>();
    }

    @Test
    public void testListUsers() {
        super.testAddAndEditUser();
        doc = tester.renderPage("UserList");
        assertNotNull(doc.getElementById("userList"));
        assertTrue(doc.getElementById("userList").find("tbody/tr").getChildren().size() >= 1);
    }
}
