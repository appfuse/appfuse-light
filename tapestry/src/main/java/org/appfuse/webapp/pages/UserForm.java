package org.appfuse.webapp.pages;

import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.alerts.Duration;
import org.apache.tapestry5.alerts.Severity;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.PageActivationContext;
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

    @Inject
    private AlertManager alertManager;

    @PageActivationContext
    @Property
    private User user;

    @Component(id = "userForm")
    private Form form;

    private boolean cancel;
    private boolean delete;

    void onPrepare() {
        if (user == null) {
            user = new User();
        }
    }

    void onValidateFromUserForm() {
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

    Object onSuccess() {
        if (delete) return onDelete();
        if (cancel) return onCancel();

        log.debug("Saving user...");
        
        try {
            userManager.saveUser(user);
        } catch (UserExistsException uex) {
            alertManager.error(messages.format("user.exists"));
            // TODO: Figure out how to repopulate the form with entered values
            return null;
        }

        String message = messages.format("user.saved", user.getFullName());
        alertManager.alert(Duration.TRANSIENT, Severity.INFO, message);

        return UserList.class;
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
        String message = messages.format("user.deleted", user.getFullName());

        userManager.removeUser(user.getId().toString());
        alertManager.alert(Duration.TRANSIENT, Severity.INFO, message);

        return UserList.class;
    }

    Object onCancel() {
        return UserList.class;
    }
}
