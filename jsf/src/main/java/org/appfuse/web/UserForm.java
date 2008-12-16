package org.appfuse.web;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.appfuse.service.UserExistsException;

public class UserForm {
    private final Log log = LogFactory.getLog(UserForm.class);
    private String id;
    public User user = new User();
    public UserManager userManager;

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

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String edit() {

        if (id != null) {
            // assuming edit
            setUser(userManager.getUser(id));
        }

        return "success";
    }

    public String save() throws UserExistsException {
        // Fix issue caused by WebTest and/or JSF
        User user = getUser();
        if (user.getId().equals(0L)) {
            user.setId(null);
        }

        userManager.saveUser(user);
        addMessage("user.saved", getUser().getFullName());

        return "success";
    }

    public String delete() {
        userManager.removeUser(getUser().getId().toString());
        addMessage("user.deleted", getUser().getFullName());

        return "success";
    }

    // Convenience methods ====================================================
    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext()
                                    .getRequestParameterMap().get(name);
    }

    public void addMessage(String key, String arg) {
        // sure is a lot of work to get the named ResourceBundle in JSF, eh?
        ApplicationFactory factory =
            (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        String bundleName = factory.getApplication().getMessageBundle();
        ResourceBundle messages = ResourceBundle.getBundle(bundleName);
        
        // it's even more work to format a message with args 
        MessageFormat form = new MessageFormat(messages.getString(key));

        String msg = form.format(new Object[] { arg });
        
        // add message to session so it can live past redirects
        // the MessageFilter class will take care of removing it
        HttpSession session =
            (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                                      .getSession(true);
        session.setAttribute("message", msg);
    }
}
