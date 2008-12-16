package org.appfuse.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserListTest extends BasePageTestCase {
    private UserList page;
    private UserManager userManager;
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void onSetUp() throws Exception {
        Map map = new HashMap();
        map.put("userManager", userManager);
        page = (UserList) getPage(UserList.class, map);
        
        // create a new user
        User user = new User();
        user.setUsername("abbie");
        user.setPassword("puppies");
        user.setFirstName("Abbie");
        user.setLastName("Raible");
        user.setEmail("abbie@appfuse.org");
        
        // persist to database
        userManager.saveUser(user);
    }

    protected void onTearDown() throws Exception {
        page = null;
    }

    public void testSearch() throws Exception {
        assertTrue(page.getUserManager().getUsers(null).size() >= 1);
    }
}
