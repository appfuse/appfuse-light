package org.appfuse.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

public class UserManagerImplTest extends MockObjectTestCase {
    private final Log log = LogFactory.getLog(UserManagerImplTest.class);
    private UserManagerImpl mgr = new UserManagerImpl();
    private Mock mockDao = null;

    protected void setUp() throws Exception {
        mockDao = new Mock(UserDao.class);
        mgr.setUserDao((UserDao) mockDao.proxy());
    }

    public void testAddAndRemoveUser() throws Exception {
        User user = new User();
        user.setFirstName("Easter");
        user.setLastName("Bunny");

        // set expected behavior on dao
        mockDao.expects(once()).method("saveUser").with(same(user));

        mgr.saveUser(user);

        // verify expectations
        mockDao.verify();

        assertEquals(user.getFullName(), "Easter Bunny");

        if (log.isDebugEnabled()) {
            log.debug("removing user...");
        }

        mockDao.expects(once()).method("removeUser")
                .with(eq(new Long(1)));

        mgr.removeUser("1");

        // verify expectations
        mockDao.verify();

        try {
            // set expectations
            Throwable ex =
                    new ObjectRetrievalFailureException(User.class, "1");
            mockDao.expects(once()).method("getUser")
                    .with(eq(new Long(1))).will(throwException(ex));

            user = mgr.getUser("1");

            // verify expectations
            mockDao.verify();
            fail("User 'Easter Bunny' found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
