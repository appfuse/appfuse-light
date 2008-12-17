package org.appfuse.web.pages;

import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.dom.Node;
import org.junit.Test;

import java.util.List;

public class UserFormTest extends BasePageTester {
    
    @Test
    public void testAddAndEditUser() {
        doc = tester.renderPage("UserForm");
        populateForm();

        assertTrue(doc.toString().contains("saved successfully"));
        Element table = doc.getElementById("userList");
        assertTrue(table.toString().contains("tapestry"));
        
        List<Node> rows = table.find("tbody").getChildren();
        String userId = "";

        if (testType.equals(MOCK)) {
            // find row with id != 1
            for (Node row : rows) {
                Element r = (Element) row;
                if (!"1".equals(r.find("td/a").getChildMarkup().trim())) {
                    userId = r.find("td/a").getChildMarkup().trim();
                    break;
                }
            }
        } else { // find first row
            userId = ((Element) rows.get(0)).find("td/a").getChildMarkup().trim();
        }

        Element idLink = table.getElementById("user" + userId);
        doc = tester.clickLink(idLink);

        Element form = doc.getElementById("userForm");
        assertNotNull(form);
        assertEquals("tapestry", form.getElementById("username").getAttribute("value"));
    }

    @Test
    public void testRemoveUser() {
        doc = tester.renderPage("UserForm");
        populateForm();

        assertTrue(doc.toString().contains("saved successfully"));
        Element table = doc.getElementById("userList");
        assertTrue(table.toString().contains("tapestry"));

        List<Node> rows = table.find("tbody").getChildren();
        String userId = "";

        if (testType.equals(MOCK)) {
            // find row with id != 1
            for (Node row : rows) {
                Element r = (Element) row;
                if (!"1".equals(r.find("td/a").getChildMarkup().trim())) {
                    userId = r.find("td/a").getChildMarkup().trim();
                    break;
                }
            }
        } else { // find first row
            userId = ((Element) rows.get(0)).find("td/a").getChildMarkup().trim();
        }

        Element idLink = table.getElementById("user" + userId);
        doc = tester.clickLink(idLink);

        Element deleteButton = doc.getElementById("delete");
        fieldValues.put("id", userId);
        doc = tester.clickSubmit(deleteButton, fieldValues);
        assertTrue(doc.toString().contains("successfully deleted"));
    }
}
