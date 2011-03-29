package org.appfuse.webapp.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UserActionTest extends BaseActionTestCase {
    private UserAction action;

    @Override
    @Before
    public void onSetUp() {

        // grab the UserManager from the ApplicationContext or mock it
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");

        action = new UserAction();
        action.setUserManager(userManager);

        // Initialize ActionContext
        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        ActionContext.getContext().setSession(new HashMap<String, Object>());
    }

    @Test
    public void testSearch() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getUsers().size() >= 1);
    }

    @Test
    public void testEdit() throws Exception {
        action.setId("-1");
        assertEquals("success", action.edit());
        assertNotNull(action.getUser().getFirstName());
    }

    @Test
    public void testSave() throws Exception {
        action.setId("-1");
        action.edit();
        assertNotNull(action.getUser());
        action.getUser().setFirstName("Jack");

        assertEquals("success", action.save());
        assertNotNull(action.getUser());
    }

    @Test
    public void testRemove() throws Exception {
        User user = new User();
        user.setId(-2L);
        action.setUser(user);
        assertEquals("delete", action.delete());
    }
}
