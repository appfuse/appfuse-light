package org.appfuse.web;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.appfuse.service.UserManager;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerTest extends MockObjectTestCase {
    private UserController c = new UserController();
    private Mock mockManager = null;

    protected void setUp() throws Exception {
        mockManager = new Mock(UserManager.class);
        c.setUserManager((UserManager) mockManager.proxy());
    }

    public void testGetUsers() throws Exception {
        // set expected behavior on manager
        mockManager.expects(once()).method("getUsers")
                   .will(returnValue(new ArrayList()));

        ModelAndView mav =
            c.handleRequest(new MockHttpServletRequest(),
                            (HttpServletResponse) null);
        Map m = mav.getModel();
        assertNotNull(m.get("users"));
        assertEquals(mav.getViewName(), "userList");
        
        // verify expectations
        mockManager.verify();
    }
}
