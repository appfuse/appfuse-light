package org.appfuse.web;

import java.util.HashMap;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

public class UserActionTest extends AbstractTransactionalDataSourceSpringContextTests {
    private UserAction action;
    private String userId;

    protected String[] getConfigLocations() {
        return new String[] {
            "classpath:/applicationContext-resources.xml",
            "classpath:/applicationContext-dao.xml",
            "classpath:/applicationContext-service.xml",
            "/WEB-INF/applicationContext*.xml"
        };
    }

    protected void onSetUp() throws Exception {
        deleteFromTables(new String[] {"app_user"});

        // grab the UserManager from the ApplicationContext or mock it
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");

        // add a test user to the database
        User user = new User();
        user.setUsername("jraible");
        user.setPassword("ilovetrains");
        user.setFirstName("Jack");
        user.setLastName("Raible");
        user.setEmail("jraible@appfuse.org");
        user = userManager.saveUser(user);
        userId = user.getId().toString();

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

    protected void onTearDown() throws Exception {
        ActionContext.getContext().setSession(null);
    }

    public void testSearch() throws Exception {
        assertEquals(action.list(), ActionSupport.SUCCESS);
        assertTrue(action.getUsers().size() >= 1);
    }

    public void testEdit() throws Exception {
        action.setId(userId);
        assertEquals(action.edit(), "success");
        assertNotNull(action.getUser().getFirstName());
    }

    public void testSave() throws Exception {
        action.setId(userId);
        action.edit();
        assertNotNull(action.getUser());
        action.getUser().setFirstName("Jack");

        assertEquals(action.save(), "success");
        assertNotNull(action.getUser());
    }

    public void testRemove() throws Exception {
        User user = new User();
        user.setId(new Long(userId));
        action.setUser(user);
        assertEquals(action.delete(), "delete");
    }
}
