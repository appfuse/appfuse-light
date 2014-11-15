package org.appfuse.webapp.action;

import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.mock.MockServletContext;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.Filter;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations = {"classpath:/applicationContext-resources.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-service.xml"})
public class UserFormBeanTest {
    private MockServletContext servletContext;

    @Test
    public void testView() throws Exception {
        // Setup the servlet engine
        servletContext = new StripesTestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(servletContext, UserFormBean.class);
        String userId = "-1";
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
