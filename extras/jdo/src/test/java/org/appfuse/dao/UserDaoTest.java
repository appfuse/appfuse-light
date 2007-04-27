package org.appfuse.dao;

import org.appfuse.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoTest extends BaseDaoTestCase {
    private User user = null;
    private UserDao dao = null;

    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
    }

    protected String[] getConfigLocations() {
        return new String[] {
            "/WEB-INF/applicationContext*.xml",
            "classpath:/applicationContext-test.xml" };
    }

    public void testGetUsers() {
        user = new User();
        user.setFirstName("Rod");
        user.setLastName("Johnson");

        dao.saveUser(user);

        assertTrue(dao.getUsers().size() >= 1);
    }

    public void testSaveUser() throws Exception {
        user = new User();
        user.setFirstName("Rod");
        user.setLastName("Johnson");

        dao.saveUser(user);
        assertTrue("primary key assigned", user.getId() != null);

        assertNotNull(user.getFirstName());
    }

    public void testAddAndRemoveUser() throws Exception {
        user = new User();
        user.setFirstName("Bill");
        user.setLastName("Joy");

        dao.saveUser(user);

        assertNotNull(user.getId());
        assertTrue(user.getFirstName().equals("Bill"));

        log.debug("removing user...");

        dao.removeUser(user.getId());
        endTransaction();

        try {
            user = dao.getUser(user.getId());
            fail("User found in database");
        } catch (DataAccessException dae) {
            log.debug("Expected exception: " + dae.getMessage());
            assertNotNull(dae);
        }
    }
}
