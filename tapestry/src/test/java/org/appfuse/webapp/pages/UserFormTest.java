package org.appfuse.webapp.pages;

import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserFormTest extends BasePageTestCase {
    
    @Test
    public void testAddAndEditUser() {
        doc = tester.renderPage("UserForm");
        populateForm();

        assertTrue(doc.toString().contains("saved successfully"));
        Element table = doc.getElementById("userList");
        assertTrue(table.toString().contains("tapestry"));
        
        List<Node> rows = table.find("tbody").getChildren();
        String userId = null;
        // loop through the rows until we find "tapestry" user
        for (Node node : rows) {
            if (((Element) node).getChildMarkup().contains("tapestry")) {
                userId = ((Element) node).find("td/a").getChildMarkup().trim();
            }
        }

        Element idLink = table.getElementById("user" + userId);
        doc = tester.clickLink(idLink);

        Element form = doc.getElementById("userForm");
        assertNotNull(form);
        assertEquals("tapestry", form.getElementById("username").getAttribute("value"));
    }

    @Test
    @Ignore // Disabled due to rollback issues
    public void testRemoveUser() {
        doc = tester.renderPage("UserForm");
        populateForm();

        assertTrue(doc.toString().contains("saved successfully"));
        Element table = doc.getElementById("userList");
        assertTrue(table.toString().contains("tapestry"));

        List<Node> rows = table.find("tbody").getChildren();
        String userId = null;
        // loop through the rows until we find "tapestry" user
        for (Node node : rows) {
            if (((Element) node).getChildMarkup().contains("tapestry")) {
                userId = ((Element) node).find("td/a").getChildMarkup().trim();
            }
        }

        Element idLink = table.getElementById("user" + userId);
        doc = tester.clickLink(idLink);

        Element deleteButton = doc.getElementById("delete");
        fieldValues.put("id", userId);
        doc = tester.clickSubmit(deleteButton, fieldValues);
        assertTrue(doc.toString().contains("successfully deleted"));
    }

    private Element populateForm() {
        doc = tester.renderPage("UserForm");
        Element form = doc.getElementById("userForm");
        assertNotNull(form);

        fieldValues.put("username", "tapestry");
        fieldValues.put("password", "isfun");
        fieldValues.put("firstName", "Tapestry");
        fieldValues.put("lastName", "5");
        fieldValues.put("email", "tapestry@appfuse.org");

        doc = tester.submitForm(form, fieldValues);
        return form;
    }
}
