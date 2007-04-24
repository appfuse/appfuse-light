package org.appfuse.web;

import net.sourceforge.stripes.action.*;
import net.sourceforge.stripes.integration.spring.SpringBean;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.orm.ObjectRetrievalFailureException;

public class UserFormBean extends BaseActionBean {
    @SpringBean
    private UserManager userManager;
    private User user;
    private String id;
    
    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    @ValidateNestedProperties({
    @Validate(field = "lastName", required = true, minlength = 4, maxlength = 50)})
    public void setUser(User user) {
        this.user = user;
    }

    @DontValidate @DefaultHandler
    public Resolution view() {
        if (id != null) {
            try {
                user = userManager.getUser(id);
            } catch (ObjectRetrievalFailureException e) {
                e.printStackTrace();
                getContext().getMessages().add(new LocalizableMessage("user.missing"));
                return showList();
            }
        } else {
            user = new User();
        }
        return new ForwardResolution("/userForm.jsp");
    }

    @HandlesEvent("save")
    public Resolution save() {
        userManager.saveUser(user);
        getContext().getMessages().add(new LocalizableMessage("user.saved", user.getFullName()));
        return showList();
    }

    @DontValidate
    @HandlesEvent("delete")
    public Resolution delete() {
        userManager.removeUser(user.getId().toString());
        getContext().getMessages().add(new LocalizableMessage("user.deleted", user.getFullName()));
        return showList();
    }

    @DontValidate
    @HandlesEvent("cancel")
    public Resolution cancel() {
        return showList();
    }

    private Resolution showList() {
        return new RedirectResolution("/users.html").flash(this);
    }
}