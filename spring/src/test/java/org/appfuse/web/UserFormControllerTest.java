package org.appfuse.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RunWith(JMock.class)
public class UserFormControllerTest {
    final Log log = LogFactory.getLog(UserFormControllerTest.class);
    UserFormController c = new UserFormController();
    MockHttpServletRequest request = null;
    ModelAndView mv = null;
    User user = new User();
    UserManager userManager = null;
    Mockery context = new JUnit4Mockery();

    @Before
    public void setUp() {
        userManager = context.mock(UserManager.class);
        // manually set properties (dependencies) on userFormController
        c.userManager = userManager;
        c.setFormView("userForm");

        // set context with messages avoid NPE when controller calls 
        // getMessageSourceAccessor().getMessage()
        StaticApplicationContext ctx = new StaticApplicationContext();
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("basename", "messages");
        ctx.registerSingleton("messageSource", ResourceBundleMessageSource.class,
                new MutablePropertyValues(properties));
        ctx.refresh();
        c.setApplicationContext(ctx);

        // setup user values
        user.setId(1L);
        user.setFirstName("Matt");
        user.setLastName("Raible");
    }

    @Test
    public void testEdit() throws Exception {
        log.debug("testing edit...");

        // set expected behavior on manager
        context.checking(new Expectations() {{
            one(userManager).getUser(with(equal("1")));
            will(returnValue(new User()));
        }});

        request = new MockHttpServletRequest("GET", "/userform.html");
        request.addParameter("id", user.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertEquals("userForm", mv.getViewName());
    }

    @Test
    public void testSave() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        context.checking(new Expectations() {{
            one(userManager).getUser(with(equal("1")));
            will(returnValue(user));
        }});

        final User savedUser = user;
        savedUser.setLastName("Updated Last Name");
        // called by onSubmit()
        context.checking(new Expectations() {{
            one(userManager).saveUser(with(equal(savedUser)));
        }});

        request = new MockHttpServletRequest("POST", "/userform.html");
        request.addParameter("id", user.getId().toString());
        request.addParameter("firstName", user.getFirstName());
        request.addParameter("lastName", "Updated Last Name");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        Errors errors = (Errors) mv.getModel().get(BindException.MODEL_KEY_PREFIX + "user");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("message"));
    }

    @Test
    public void testRemove() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        context.checking(new Expectations() {{
            one(userManager).getUser(with(equal("1")));
            will(returnValue(user));
        }});

        // called by onSubmit()
        context.checking(new Expectations() {{
            one(userManager).removeUser("1");
        }});

        request = new MockHttpServletRequest("POST", "/userform.html");
        request.addParameter("delete", "");
        request.addParameter("id", user.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertNotNull(request.getSession().getAttribute("message"));
    }
}
