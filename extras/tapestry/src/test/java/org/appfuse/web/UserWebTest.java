package org.appfuse.web;

import net.sourceforge.jwebunit.WebTestCase;

public class UserWebTest extends WebTestCase {
	
    public UserWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080");
        getTestContext().setResourceBundleName("messages");
    }
    
    public void testWelcomePage() {
        beginAt("/");
        assertTitleEqualsKey("index.title");
    }
    
    public void testAddUser() {
        beginAt("/userForm.html");
        assertTitleEqualsKey("userForm.title");
        setFormElement("firstName", "Spring");
        setFormElement("lastName", "User");
        submit("save");
        assertTextPresent("saved successfully");
    }
    
    public void testListUsers() {
        beginAt("/users.html");   	
        
        // check that table is present
        assertTablePresent("userList");

        // check that a set of strings are present somewhere in table
        assertTextInTable("userList", new String[] {"Spring", "User"});
    }
    
    public void testEditUser() {
        beginAt("/users,$DirectLink.sdirect?sp=l" + getInsertedUserId());
        assertFormElementEquals("firstName", "Spring");
        submit("save");
        assertTitleEqualsKey("userList.title");
    }

    /**
     * Convenience method to get the id of the inserted user
     * Assumes last inserted user is "Spring User"
     */
    public String getInsertedUserId() {
        beginAt("/users.html");
        assertTablePresent("userList");
        assertTextInTable("userList", "Spring");
        String[][] sparseTableCellValues =
                getDialog().getSparseTableBySummaryOrId("userList");
        return sparseTableCellValues[sparseTableCellValues.length-1][0];
    }
}
