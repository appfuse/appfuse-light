package org.appfuse.webapp;

import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class UserListBeanTest extends AbstractTransactionalDataSourceSpringContextTests {
    protected String[] getConfigLocations() {
        return new String[]{
                "classpath:/applicationContext-resources.xml",
                "classpath:/applicationContext-dao.xml",
                "classpath:/applicationContext-service.xml"
        };
    }

    protected void onSetUp() throws Exception {
        deleteFromTables(new String[]{"app_user"});

        // grab the UserManager from the ApplicationContext or mock it
        UserManager mgr = (UserManager) applicationContext.getBean("userManager");

        // add a test user to the database
        User user = new User();
        user.setUsername("jack");
        user.setPassword("trains");
        user.setFirstName("Jack");
        user.setLastName("Raible");
        user.setEmail("jack@appfuse.org");
        mgr.saveUser(user);
    }

    public void testExecute() throws Exception {
        // Setup the servlet engine
        MockServletContext ctx = new StripesTestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(ctx, UserListBean.class);
        trip.execute();

        UserListBean bean = trip.getActionBean(UserListBean.class);
        assertTrue(bean.getUsers().size() > 0);
    }
}
