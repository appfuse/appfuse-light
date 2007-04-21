package org.appfuse.dao.hibernate;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve User objects.
 *
 * @author Matt Raible
 */
public class UserDaoHibernate extends HibernateDaoSupport implements UserDao {

    public List getUsers() {
        return getHibernateTemplate().find("from User");
    }

    public User getUser(Long id) {
        User user = (User) getHibernateTemplate().get(User.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }

    public void saveUser(User user) {
        getHibernateTemplate().saveOrUpdate(user);

        if (logger.isDebugEnabled()) {
            logger.debug("userId set to: " + user.getId());
        }
    }

    public void removeUser(Long id) {
        getHibernateTemplate().delete(getUser(id));
    }
}
