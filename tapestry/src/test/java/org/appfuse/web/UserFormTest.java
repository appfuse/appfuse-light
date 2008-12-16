package org.appfuse.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry.PageRedirectException;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.engine.RequestCycle;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserFormTest extends BasePageTestCase {
    private UserForm page;
    private Long userId;
    private UserManager userManager;
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    protected void onSetUp() throws Exception {
        deleteFromTables(new String[] {"app_user"});
        
        Map map = new HashMap();
        map.put("userManager", userManager);
        map.put("engineService", new MockPageService());
        page = (UserForm) getPage(UserForm.class, map);
      
        // create a new user
        User user = new User();
        user.setUsername("jack");
        user.setPassword("trains");
        user.setFirstName("Jack");
        user.setLastName("Raible");
        user.setEmail("jack@appfuse.org");
        
        // persist to database
        userManager.saveUser(user);
        userId = user.getId();
    }
    
    protected void onTearDown() throws Exception {
        page = null;
    }
    
    public void testEdit() throws Exception {
        RequestCycle cycle = new MockRequestCycle();
        cycle.setListenerParameters(new Object[] {userId});
        
        page.edit(cycle);
        assertNotNull(page.getUser());
    }
    
    public void testSave() throws Exception {      
        RequestCycle cycle = new MockRequestCycle();
        cycle.setListenerParameters(new Object[] {userId});
        
        page.edit(cycle);
        assertNotNull(page.getUser());
        ILink link = page.save(cycle);
        assertEquals("users" + EXTENSION, link.getURL());
    }
    
    public void testRemove() throws Exception {
        User user = new User();
        user.setId(userId);
        page.setUser(user);
        
        ILink link = page.delete(new MockRequestCycle());
        assertEquals("users" + EXTENSION, link.getURL());
    }
}
