package org.appfuse.web;

import java.util.Date;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserListTest extends BasePageTestCase {
    private UserList bean;

    protected void setUp() throws Exception {    
        super.setUp();
        bean = (UserList) getManagedBean("userList");
        
        // create a new user
        User user = new User();
        user.setFirstName("Matt");
        user.setLastName("Raible");
        user.setBirthday(new Date());
        
        // persist to database
        UserManager userManager = (UserManager) ctx.getBean("userManager");
        userManager.saveUser(user);
    }
    
    public void testSearch() throws Exception {
        assertTrue(bean.getUsers().size() >= 1);
    }
}
