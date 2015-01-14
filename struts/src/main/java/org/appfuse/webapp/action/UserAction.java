package org.appfuse.webapp.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;

import java.util.ArrayList;
import java.util.List;

public class UserAction extends BaseAction {
    private final Log log = LogFactory.getLog(UserAction.class);
    private UserManager userManager;
    private List<User> users;
    private User user;
    private String id;

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String delete() {
        userManager.removeUser(user.getId().toString());

        List<String> args = new ArrayList<String>();
        args.add(user.getFullName());

        saveMessage(getText("user.deleted", args));

        return "delete";
    }

    public String edit() {
        // check for an add
        if (id != null) {
            user = userManager.getUser(id);
        } else {
            user = new User();
        }
        return SUCCESS;
    }

    public String execute() {
        if (cancel != null) {
            return "cancel";
        }

        if (delete != null) {
            return delete();
        }

        return SUCCESS;
    }

    public String save() throws UserExistsException {
        if (log.isDebugEnabled()) {
            log.debug("entering 'save' method");
        }

        try {
            userManager.saveUser(user);
        } catch (UserExistsException uex) {
            addActionError(getText("user.exists"));
            return INPUT;
        }

        List<String> args = new ArrayList<String>();
        args.add(user.getFullName());

        saveMessage(getText("user.saved", args));

        return SUCCESS;
    }

    public String list() {
        users = userManager.getUsers();
        return SUCCESS;
    }
}
