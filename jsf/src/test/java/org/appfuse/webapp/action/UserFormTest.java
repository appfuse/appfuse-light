package org.appfuse.webapp.action;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserFormTest extends BasePageTestCase {
    private UserForm bean;
    @Autowired
    private UserManager userManager;

    @Before
    public void onSetUp(){
        super.onSetUp();

        bean = new UserForm();
        bean.setUserManager(userManager);
    }
    
    @After
    public void onTearDown() {
        super.onTearDown();
        bean = null;
    }

    @Test
    public void testEdit() throws Exception {
        bean.setId("-1");
        assertEquals(bean.edit(), "success");
        assertNotNull(bean.getUser().getFirstName());
    }

    @Test
    public void testSave() throws Exception {
        bean.setId("-1");
        bean.edit();
        assertNotNull(bean.getUser());
        bean.getUser().setFirstName("Jack");

        assertEquals(bean.save(), "success");
        assertNotNull(bean.getUser());
    }
    
    @Test
    public void testRemove() throws Exception {
        User user = new User();
        user.setId(-2L);
        bean.setUser(user);
        assertEquals(bean.delete(), "success");
    }
}
