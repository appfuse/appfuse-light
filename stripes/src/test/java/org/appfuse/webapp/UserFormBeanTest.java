package org.appfuse.webapp;

import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.mock.MockRoundtrip;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;

public class UserFormBeanTest extends AbstractTransactionalDataSourceSpringContextTests {
    private String userId;
    
    protected String[] getConfigLocations() {
        return new String[] {
            "classpath:/applicationContext-resources.xml",
            "classpath:/applicationContext-dao.xml",
            "classpath:/applicationContext-service.xml"
        };
    }

    protected void onSetUp() throws Exception {
        deleteFromTables(new String[] {"app_user"});
        
        // grab the UserManager from the ApplicationContext or mock it
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");

        // add a test user to the database
        User user = new User();
        user.setUsername("jack");
        user.setPassword("trains");
        user.setFirstName("Jack");
        user.setLastName("Raible");
        user.setEmail("jack@appfuse.org");
        user = userManager.saveUser(user);
        userId = user.getId().toString();
    }

    public void testView() throws Exception {
        // Setup the servlet engine
        MockServletContext ctx = new StripesTestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(ctx, UserFormBean.class);
        trip.addParameter("id", userId);
        trip.execute();

        UserFormBean bean = trip.getActionBean(UserFormBean.class);
        assertNotNull(bean.getUser());
    }
}
