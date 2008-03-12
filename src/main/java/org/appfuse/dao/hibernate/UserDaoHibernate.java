package org.appfuse.dao.hibernate;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.SessionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;


/**
 * This class interacts with Spring and Hibernate to save and
 * retrieve User objects.
 *
 * @author Matt Raible
 */
@Repository(value = "userDao")
public class UserDaoHibernate implements UserDao {
    HibernateTemplate hibernateTemplate;
    Log logger = LogFactory.getLog(UserDaoHibernate.class);
    
    @Autowired
    public UserDaoHibernate(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public List getUsers() {
        return hibernateTemplate.find("from User");
    }

    public User getUser(Long id) {
        User user = (User) hibernateTemplate.get(User.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, id);
        }
        return user;
    }

    public void saveUser(User user) {
        hibernateTemplate.saveOrUpdate(user);

        if (logger.isDebugEnabled()) {
            logger.debug("userId set to: " + user.getId());
        }
    }

    public void removeUser(Long id) {
        hibernateTemplate.delete(getUser(id));
    }
}
