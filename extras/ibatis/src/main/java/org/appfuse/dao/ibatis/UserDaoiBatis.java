package org.appfuse.dao.ibatis;

import java.util.List;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;


/**
 * This class interacts with iBatis's SQL Maps to save and retrieve User
 * related objects.
 *
 * @author Matt Raible
 */
public class UserDaoiBatis extends SqlMapClientDaoSupport implements UserDao {
    
    private DataFieldMaxValueIncrementer incrementer;
    
    public void setIncrementer(DataFieldMaxValueIncrementer incrementer) {
        this.incrementer = incrementer;
    }

    public List getUsers() {
        return getSqlMapClientTemplate().queryForList("getUsers", null);
    }

    public User getUser(Long id) {
        User user =
            (User) getSqlMapClientTemplate().queryForObject("getUser", id);

        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, id);
        }

        return user;
    }

    public void saveUser(User user) {
        if (user.getId() == null) {
            Long id = new Long(incrementer.nextLongValue());
            user.setId(id);

            // To use iBatis's <selectKey> feature, which is db-specific, comment
            // out the above two lines and use the line below instead
            
            // Long id = (Long) getSqlMapClientTemplate().insert("addUser", user);
            getSqlMapClientTemplate().insert("addUser", user);
            logger.info("new User id set to: " + id);
        } else {
            getSqlMapClientTemplate().update("updateUser", user);
        }
    }

    public void removeUser(Long id)  {
        getSqlMapClientTemplate().update("deleteUser", id);
    }
}
