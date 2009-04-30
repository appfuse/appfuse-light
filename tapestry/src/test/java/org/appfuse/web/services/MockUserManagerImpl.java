package org.appfuse.web.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.appfuse.service.impl.UserManagerImpl;

public class MockUserManagerImpl extends UserManagerImpl implements UserManager {
    private List<User> users = new ArrayList<User>();

    public MockUserManagerImpl() {
        User user = new User();
        user.setId(1L);
        user.setUsername("mraible");
        user.setFirstName("Matt");
        user.setLastName("Raible");
        user.setEmail("mraible@gmail.com");
        users.add(user);
    }

    @Override
    public User getUser(String userId) {
        for (User user : users) {
            if (user.getId().equals(new Long(userId))) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public User saveUser(User user) throws UserExistsException {
        if (!users.contains(user)) {
            if (user.getId() == null) {
                user.setId(new Random().nextLong());
                log.debug("Set userId to: " + user.getId());

                users.add(user);
            }
        }
        return user;
    }

    @Override
    public void removeUser(String userId) {
        List<User> it = new ArrayList<User>(users);
        for (User user : it) {
            if (user.getId().equals(new Long(userId))) {
                users.remove(user);
            }
        }
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}