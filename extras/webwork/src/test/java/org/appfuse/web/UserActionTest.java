package org.appfuse.web;

import java.util.HashMap;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

public class UserActionTest extends AbstractDependencyInjectionSpringContextTests {
    private UserAction action;
    private String userId;

    public void setUserAction(UserAction action) {
        this.action = action;
    }

    protected String[] getConfigLocations() {
        return new String[] {"/WEB-INF/applicationContext*.xml", "/WEB-INF/action-servlet.xml"};
    }

    protected void onSetUp() throws Exception {
        // grab the UserManager from the ApplicationContext or mock it
        UserManager mgr = (UserManager) applicationContext.getBean("userManager");

        // add a test user to the database
        User user = new User();
        user.setFirstName("Jack");
        user.setLastName("Raible");
        mgr.saveUser(user);
        userId = user.getId().toString();

        ActionContext.getContext().setSession(new HashMap());
    }

    protected void onTearDown() throws Exception {
        ActionContext.getContext().setSession(null);
    }

    public void testSearch() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getUsers().size() >= 1);
    }

    public void testEdit() throws Exception {
        action.setId(userId);
        assertEquals(action.edit(), "success");
        assertNotNull(action.getUser().getFirstName());
    }

    public void testSave() throws Exception {
        action.setId(userId);
        action.edit();
        assertNotNull(action.getUser());
        action.getUser().setFirstName("Jack");

        assertEquals(action.save(), "success");
        assertNotNull(action.getUser());
    }

    public void testRemove() throws Exception {
        User user = new User();
        user.setId(new Long(userId));
        action.setUser(user);
        assertEquals(action.delete(), "delete");
    }
}
