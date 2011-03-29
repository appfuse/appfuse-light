package org.appfuse.webapp.pages;

import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.corelib.components.Form;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.appfuse.service.UserExistsException;
import org.appfuse.webapp.util.MessageUtil;
import org.slf4j.Logger;

public class UserForm {
    @Inject
    private Logger log;

    @Inject
    private Messages messages;

    @Inject
    private UserManager userManager;

    @Persist
    private User user;

    public User getUser() {
        return user;
    }

    @Component(id = "userForm")
    private Form form;

    private boolean cancel;
    private boolean delete;

    /**
     * Allows setting user object from another class (i.e. UserList)
     *
     * @param user an initialized instance
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    @InjectPage
    private UserList userList;

    void onValidateForm() {
        if (!delete && !cancel) {
            // manually validate required fields since we don't have access
            // to annotate the user object
            if (user.getUsername() == null || user.getUsername().trim().equals("")) {
                form.recordError("Username is a required field.");
            }
            if (user.getPassword() == null || user.getPassword().trim().equals("")) {
                form.recordError("Password is a required field.");
            }
            if (user.getEmail() == null || user.getEmail().trim().equals("")) {
                form.recordError("E-Mail is a required field.");
            }
        }
    }

    void onActivate(String id) {
        if (id != null) {
            user = userManager.getUser(id);
        }
    }

    Object onSuccess() throws UserExistsException {
        if (delete) return onDelete();
        if (cancel) return onCancel();

        log.debug("Saving user...");
        
        userManager.saveUser(user);

        String msg = MessageUtil.convert(messages.get("user.saved"));
        String message = String.format(msg, user.getFullName());

        userList.setMessage(message);
        return userList;
    }

    void onSelectedFromDelete() {
        log.debug("Deleting user...");
        delete = true; 
    }

    void onSelectedFromCancel() {
        log.debug("Cancelling form...");
        cancel = true;
    }

    Object onDelete() {
        String msg = MessageUtil.convert(messages.get("user.deleted"));
        String message = String.format(msg, user.getFullName());

        userManager.removeUser(user.getId().toString());
        userList.setMessage(message);
        return userList;
    }

    Object onCancel() {
        return userList;
    }
}
