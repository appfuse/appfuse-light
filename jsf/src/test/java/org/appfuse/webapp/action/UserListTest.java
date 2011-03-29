package org.appfuse.webapp.action;

import org.appfuse.service.UserManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserListTest extends BasePageTestCase {
    private UserList bean;
    @Autowired
    private UserManager userManager;
    
    @Before
    public void onSetUp() {
        super.onSetUp();

        bean = new UserList();
        bean.setUserManager(userManager);
    }

    @Test
    public void testSearch() throws Exception {
        assertTrue(bean.getUsers().size() >= 1);
    }
}
