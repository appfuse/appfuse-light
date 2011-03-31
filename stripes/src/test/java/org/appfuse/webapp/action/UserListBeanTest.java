package org.appfuse.webapp.action;

import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.After;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import javax.servlet.Filter;

import static org.junit.Assert.assertTrue;

@ContextConfiguration(
        locations = {"classpath:/applicationContext-resources.xml",
                "classpath:/applicationContext-dao.xml",
                "classpath:/applicationContext-service.xml"})
public class UserListBeanTest extends AbstractTransactionalJUnit4SpringContextTests {
    private MockServletContext servletContext;

    @Test
    public void testExecute() throws Exception {
        // Setup the servlet engine
        servletContext = new StripesTestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(servletContext, UserListBean.class);
        trip.execute();

        UserListBean bean = trip.getActionBean(UserListBean.class);
        assertTrue(bean.getUsers().size() > 0);
    }

    @After
    public void onTearDown() {
        // http://www.stripesframework.org/jira/browse/STS-714
        for (Filter filter : servletContext.getFilters()) {
            filter.destroy();
        }
    }
}
