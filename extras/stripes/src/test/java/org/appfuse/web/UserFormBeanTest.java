package org.appfuse.web;

import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.mock.MockRoundtrip;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class UserFormBeanTest extends AbstractDependencyInjectionSpringContextTests {
    private String userId;
    
    protected String[] getConfigLocations() {
        return new String[] {"/WEB-INF/applicationContext*.xml"};
    }

    protected void onSetUp() throws Exception {
        // grab the UserManager from the ApplicationContext or mock it
        UserManager mgr = (UserManager) applicationContext.getBean("userManager");

        // add a test user to the database
        User user = new User();
        user.setFirstName("Jack");
        user.setLastName("Raible");
        mgr.saveUser(user);
        userId = user.getId().toString();
    }

    public void testView() throws Exception {
        // Setup the servlet engine
        MockServletContext ctx = new TestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(ctx, UserFormBean.class);
        trip.addParameter("id", userId);
        trip.execute();

        UserFormBean bean = trip.getActionBean(UserFormBean.class);
        assertNotNull(bean.getUser());
    }
}
