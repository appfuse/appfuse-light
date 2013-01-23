package org.appfuse.webapp.action;

import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;

@Component("userForm")
@Scope("request")
public class UserForm extends BasePage {
    private String id;
    public User user = new User();
    public UserManager userManager;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String getId() {
        return id;
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

    public String edit() {
        // Workaround for not being able to set the id using #{param.id} when using Spring-configured managed-beans
        if (id == null) {
            id = getParameter("id");
        }
                
        if (id != null) {
            // assuming edit
            setUser(userManager.getUser(id));
        }

        return "success";
    }

    public String save() {
        // For some reason, WebTest + Tomcat causes version to be 0. Works fine on Jetty.
        if (user.getId() != null && user.getId() == 0) {
            user.setId(null);
        }

        if (user.getId() == null) {
            user.setVersion(null);
        }

        try {
            userManager.saveUser(user);
        } catch (UserExistsException uex) {
            addError("user.exists");
            return "error";
        }

        addMessage("user.saved", user.getFullName());

        return "success";
    }

    public String delete() {
        userManager.removeUser(user.getId().toString());
        addMessage("user.deleted", user.getFullName());

        return "success";
    }
}
