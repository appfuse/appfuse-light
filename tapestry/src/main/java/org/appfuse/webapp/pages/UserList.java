package org.appfuse.webapp.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.corelib.components.EventLink;
import org.apache.tapestry5.PersistenceConstants;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.slf4j.Logger;

import java.util.List;

public class UserList {
    @Inject
    private Logger log;

    @Persist(PersistenceConstants.FLASH)
    private String message;

    @Inject
    private UserManager userManager;

    @Property
    private User user;

    @InjectPage
    private UserForm form;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<User> getUsers() {
        return userManager.getAll();
    }

    Object onAdd() {
        form.setUser(new User());
        return form;
    }

    Object onActionFromEdit(User user) {
        log.debug("fetching user with id: " + user.getId());
        form.setUser(user);
        return form;
    }
}
