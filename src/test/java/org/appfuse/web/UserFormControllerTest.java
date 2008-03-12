package org.appfuse.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;


public class UserFormControllerTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(UserFormControllerTest.class);
    private UserFormController c = new UserFormController();
    private MockHttpServletRequest request = null;
    private ModelAndView mv = null;
    private User user = new User();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        super.setUp();
        mockManager = new Mock(UserManager.class);
        
        // manually set properties (dependencies) on userFormController
        c.userManager = (UserManager) mockManager.proxy();
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
    
    public void testEdit() throws Exception {
        log.debug("testing edit...");
        
        // set expected behavior on manager
        mockManager.expects(once()).method("getUser")
                   .will(returnValue(new User()));
        
        request = new MockHttpServletRequest("GET", "/userform.html");
        request.addParameter("id", user.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertEquals("userForm", mv.getViewName());
        
        // verify expectations
        mockManager.verify();
    }

    public void testSave() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        mockManager.expects(once()).method("getUser")
                   .will(returnValue(user));
        
        User savedUser = user;
        savedUser.setLastName("Updated Last Name");
        // called by onSubmit()
        mockManager.expects(once()).method("saveUser")
                   .with(eq(savedUser));
        
        request = new MockHttpServletRequest("POST", "/userform.html");
        request.addParameter("id", user.getId().toString());
        request.addParameter("firstName", user.getFirstName());
        request.addParameter("lastName", "Updated Last Name");
        mv = c.handleRequest(request, new MockHttpServletResponse());
        Errors errors = (Errors) mv.getModel().get(BindException.MODEL_KEY_PREFIX + "user");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("message"));
        
        // verify expectations
        mockManager.verify();
    }
    
    public void testRemove() throws Exception {
        // set expected behavior on manager
        // called by formBackingObject()
        mockManager.expects(once()).method("getUser")
                   .will(returnValue(user));
        // called by onSubmit()
        mockManager.expects(once()).method("removeUser").with(eq("1"));
        
        request = new MockHttpServletRequest("POST", "/userform.html");
        request.addParameter("delete", "");
        request.addParameter("id", user.getId().toString());
        mv = c.handleRequest(request, new MockHttpServletResponse());
        assertNotNull(request.getSession().getAttribute("message"));
        
        // verify expectations
        mockManager.verify();
    }   
}
