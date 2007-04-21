package org.appfuse.dao.jdo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.jdo.support.JdoDaoSupport;

/**
 * This class interacts with Spring and JDO to save and
 * retrieve User objects.
 *
 * @author Matt Raible
 */
public class UserDaoJdo extends JdoDaoSupport implements UserDao {
    
    public List getUsers() {  
        Collection users = getJdoTemplate().find(User.class);
        users = getPersistenceManager().detachCopyAll(users);
        return new ArrayList(users);
    }

    public User getUser(Long id) {
        User user = (User) getJdoTemplate().getObjectById(User.class, id);
        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, id);   
        }
        return (User) getPersistenceManager().detachCopy(user);
    }

    public void saveUser(User user) {
        getJdoTemplate().makePersistent(user);
    }

    public void removeUser(Long id) {
        getJdoTemplate().deletePersistent(
                getJdoTemplate().getObjectById(User.class, id));
    }
}
