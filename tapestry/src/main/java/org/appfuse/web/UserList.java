package org.appfuse.web;

import org.apache.tapestry.html.BasePage;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import java.util.List;

public abstract class UserList extends BasePage {
    public abstract UserManager getUserManager();
    public abstract String getMessage();
    public abstract void setMessage(String message);
    
    public List<User> getUsers() {
        return getUserManager().getUsers(null);
    }
}
