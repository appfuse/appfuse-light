package org.appfuse.web;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import java.util.Date;
import java.util.List;

public class UserActionTest extends BaseStrutsTestCase {
    private static Long userId = null;
    
    public UserActionTest(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
        super.setUp();
        UserManager mgr = (UserManager) ctx.getBean("userManager");
        User user = new User();
        user.setFirstName("Juergen");
        user.setLastName("Hoeller");
        user.setBirthday(new Date());
        mgr.saveUser(user);
        userId = user.getId();
    }

    public void testEdit() {
        // edit newly added user
        setRequestPathInfo("/user");
        addRequestParameter("method", "edit");
        addRequestParameter("id", userId.toString());
        actionPerform();
        verifyForward("edit");
        verifyNoActionErrors();
    }

    public void testDelete() {
        // delete new user
        setRequestPathInfo("/user");
        addRequestParameter("method", "delete");
        addRequestParameter("user.id", userId.toString());
        actionPerform();
        verifyForward("users");
        verifyNoActionErrors();
    }

    public void testList() {        
        setRequestPathInfo("/user");
        addRequestParameter("method", "list");
        actionPerform();
        verifyForward("list");
        verifyNoActionErrors();

        List users = (List) getRequest().getAttribute("users");
        assertNotNull(users);
    }
}
