package org.appfuse.web;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;


public class UserFormTest extends BasePageTestCase {
    private UserForm bean;
    private String userId;

    protected void setUp() throws Exception {    
        super.setUp();
        bean = (UserForm) getManagedBean("userForm");
        // verify that "userForm" has been defined as a managed-bean
        assertNotNull("you need to define 'userForm' in faces-config.xml", bean);
        
        // create a new user
        User user = new User();
        user.setFirstName("Matt");
        user.setLastName("Raible");
        
        // persist to database
        UserManager userManager = (UserManager) ctx.getBean("userManager");
        userManager.saveUser(user);
        userId = user.getId().toString();
    }
    
    protected void tearDown() throws Exception {
        super.tearDown();
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
