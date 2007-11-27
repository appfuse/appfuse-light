package org.appfuse.web;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

public class UserControllerTest extends MockObjectTestCase {
    private UserController c = new UserController();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        mockManager = new Mock(UserManager.class);
        c.setUserManager((UserManager) mockManager.proxy());
    }

    public void testGetUsers() throws Exception {
        // set expected behavior on manager
        User user1 = new User();
        user1.setFirstName("ControllerTest");
        List<User> users = new ArrayList<User>();
        users.add(user1);
        
        mockManager.expects(once()).method("getUsers")
                   .will(returnValue(users));

        ModelMap map = new ModelMap();
        String result = c.execute(map);
        assertFalse(map.isEmpty());
        assertNotNull(map.get("userList"));
        assertEquals("userList", result);
        
        // verify expectations
        mockManager.verify();
    }
}
