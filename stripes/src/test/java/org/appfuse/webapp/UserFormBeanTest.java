package org.appfuse.webapp;

import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.After;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.servlet.Filter;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(
        locations = {"classpath:/applicationContext-resources.xml",
                "classpath:/applicationContext-dao.xml",
                "classpath:/applicationContext-service.xml"})
public class UserFormBeanTest extends AbstractTransactionalJUnit4SpringContextTests {
    private String userId = "-1";
    private MockServletContext servletContext;

    @Test
    public void testView() throws Exception {
        // Setup the servlet engine
        servletContext = new StripesTestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(servletContext, UserFormBean.class);
        trip.addParameter("id", userId);
        trip.execute();

        UserFormBean bean = trip.getActionBean(UserFormBean.class);
        assertNotNull(bean.getUser());
    }

    @After
    public void onTearDown() {
        // http://www.stripesframework.org/jira/browse/STS-714
        for (Filter filter : servletContext.getFilters()) {
            filter.destroy();
        }
    }
}
