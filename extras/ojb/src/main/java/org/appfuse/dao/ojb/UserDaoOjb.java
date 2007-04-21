package org.appfuse.dao.ojb;

import java.util.ArrayList;
import java.util.List;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryByCriteria;

import org.appfuse.dao.UserDao;
import org.appfuse.model.User;

import org.springframework.orm.ObjectRetrievalFailureException;
import org.springmodules.orm.ojb.support.PersistenceBrokerDaoSupport;

public class UserDaoOjb extends PersistenceBrokerDaoSupport implements UserDao {
    
    public List getUsers() {
        return new ArrayList(getPersistenceBrokerTemplate().
                getCollectionByQuery(new QueryByCriteria(User.class)));
    }

    public User getUser(Long id) {
        Criteria criteria = new Criteria();
        criteria.addEqualTo("id", id);
        User user = (User) getPersistenceBrokerTemplate()
                    .getObjectByQuery(new QueryByCriteria(User.class, criteria));
        if (user == null) {
            throw new ObjectRetrievalFailureException(User.class, id);
        }

        return user;
    }

    public void saveUser(User user) {
        getPersistenceBrokerTemplate().store(user);
    }

    public void removeUser(Long id) {
        getPersistenceBrokerTemplate().delete(getUser(id));
    }
}
