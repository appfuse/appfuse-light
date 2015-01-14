package org.appfuse.webapp.action;

import com.opensymphony.xwork2.ActionSupport;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserActionTest extends BaseActionTestCase {
    private UserAction action;

    @Before
    public void onSetUp() {
        super.onSetUp();
        // grab the UserManager from the ApplicationContext or mock it
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");

        action = new UserAction();
        action.setUserManager(userManager);
    }

    @Test
    public void testSearch() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getUsers().size() >= 1);
    }

    @Test
    public void testEdit() throws Exception {
        action.setId("-1");
        assertEquals("success", action.edit());
        assertNotNull(action.getUser().getFirstName());
    }

    @Test
    public void testSave() throws Exception {
        action.setId("-1");
        action.edit();
        assertNotNull(action.getUser());
        action.getUser().setFirstName("Jack");

        assertEquals("success", action.save());
        assertNotNull(action.getUser());
    }

    @Test
    public void testRemove() throws Exception {
        User user = new User();
        user.setId(-2L);
        action.setUser(user);
        assertEquals("delete", action.delete());
    }
}
