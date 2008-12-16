package org.appfuse.web;

import java.util.Date;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserListTest extends BasePageTestCase {
    private UserList bean;

    protected void onSetUp() throws Exception {    
        super.onSetUp();
        deleteFromTables(new String[] {"app_user"});
        
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");
        bean = new UserList();
        bean.setUserManager(userManager);

        // create a new user
        User user = new User();
        user.setUsername("mraible");
        user.setFirstName("Matt");
        user.setLastName("Raible");
        user.setEmail("mraible@gmail.com");
        
        // persist to database
        userManager.saveUser(user);
    }
    
    public void testSearch() throws Exception {
        assertTrue(bean.getUsers().size() >= 1);
    }
}
