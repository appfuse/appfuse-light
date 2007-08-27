package org.appfuse.web;

import java.util.Date;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserListTest extends BasePageTestCase {
    private UserList bean;

    protected void onSetUp() throws Exception {    
        super.onSetUp();

        UserManager userManager = (UserManager) applicationContext.getBean("userManager");
        bean = new UserList();
        bean.setUserManager(userManager);

        // create a new user
        User user = new User();
        user.setFirstName("Matt");
        user.setLastName("Raible");
        user.setBirthday(new Date());
        
        // persist to database
        userManager.saveUser(user);
    }
    
    public void testSearch() throws Exception {
        assertTrue(bean.getUsers().size() >= 1);
    }
}
