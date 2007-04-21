package org.appfuse.dao;

import org.appfuse.model.User;

import java.util.List;


public interface UserDao extends Dao {
    public List getUsers();
    public User getUser(Long userId);
    public void saveUser(User user);
    public void removeUser(Long userId);
}
