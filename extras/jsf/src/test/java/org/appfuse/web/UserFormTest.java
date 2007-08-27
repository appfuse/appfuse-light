package org.appfuse.web;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserFormTest extends BasePageTestCase {
    private UserForm bean;
    private String userId;
    private UserManager userManager;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    protected void onSetUp() throws Exception {
        super.onSetUp();
        
        bean = new UserForm();
        bean.setUserManager(userManager);
        
        // create a new user
        User user = new User();
        user.setFirstName("Matt");
        user.setLastName("Raible");
        
        // persist to database
        userManager.saveUser(user);
        userId = user.getId().toString();
    }
    
    protected void onTearDown() throws Exception {
        super.onTearDown();
        bean = null;
    }

    public void testEdit() throws Exception {
        bean.setId(userId);
        assertEquals(bean.edit(), "success");
        assertNotNull(bean.getUser().getFirstName());
    }

    public void testSave() throws Exception {
        bean.setId(userId);
        bean.edit();
        assertNotNull(bean.getUser());
        bean.getUser().setFirstName("Jack");

        assertEquals(bean.save(), "success");
        assertNotNull(bean.getUser());
    }
    
    public void testRemove() throws Exception {
        User user = new User();
        user.setId(new Long(userId));
        bean.setUser(user);
        assertEquals(bean.delete(), "success");
    }
}
