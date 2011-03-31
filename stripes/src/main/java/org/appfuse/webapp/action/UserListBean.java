package org.appfuse.webapp.action;

import java.util.List;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.appfuse.service.UserManager;
import org.appfuse.model.User;

@UrlBinding("/users.action")
public class UserListBean extends BaseActionBean {
    @SpringBean
    private UserManager userManager;
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    @DefaultHandler
    public final Resolution execute() {
        users = userManager.getUsers();
        return new ForwardResolution("/userList.jsp");
    }
}