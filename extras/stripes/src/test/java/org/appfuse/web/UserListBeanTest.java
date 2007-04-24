package org.appfuse.web;

import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.mock.MockRoundtrip;
import junit.framework.TestCase;

public class UserListBeanTest extends TestCase {

    public void testExecute() throws Exception {
        // Setup the servlet engine
        MockServletContext ctx = new TestFixture().getServletContext();

        MockRoundtrip trip = new MockRoundtrip(ctx, UserListBean.class);
        trip.execute();

        UserListBean bean = trip.getActionBean(UserListBean.class);
        assertTrue(bean.getUsers().size() > 0);
    }
}
