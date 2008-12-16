package org.appfuse.web.pages;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import org.apache.wicket.model.LoadableDetachableModel;

/**
 * A detachable model that can load an User object from persistent store.
 * 
 * @author ivaynberg
 */
public class UserModel extends LoadableDetachableModel {
    private UserManager userManager;
    private Long id;
    
    public UserModel(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * @param user object this model will represent
     * @param userManager the userManager 
     */
    public UserModel(User user, UserManager userManager) {
        super(user);
        this.id = user.getId();
        this.userManager = userManager;
    }

    protected Object load() {
        return userManager.getUser(String.valueOf(id));
    }

}
