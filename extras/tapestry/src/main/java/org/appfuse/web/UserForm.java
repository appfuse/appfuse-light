package org.appfuse.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.event.PageBeginRenderListener;
import org.apache.tapestry.event.PageEvent;
import org.apache.tapestry.html.BasePage;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidationDelegate;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public abstract class UserForm extends BasePage implements PageBeginRenderListener {
    private final Log log = LogFactory.getLog(UserForm.class);
    public abstract IEngineService getEngineService();
    public abstract UserManager getUserManager();
    public abstract void setUser(User user);
    public abstract User getUser();

    protected IValidationDelegate getValidationDelegate() {
        // be nice to unit tests
        if (getSpecification() != null) {
            return (IValidationDelegate) getBeans().getBean("delegate");
        } else {
            return new ValidationDelegate();
        }
    }
    
    public void pageBeginRender(PageEvent event) {
        if ((getUser() == null) && !event.getRequestCycle().isRewinding()) {
            setUser(new User());
        } else if (event.getRequestCycle().isRewinding()) {
            setUser(new User());
        }
    }

    public void cancel(IRequestCycle cycle) {
        if (log.isDebugEnabled()) {
            log.debug("entered 'cancel' method");
        }

        cycle.activate("users");
    }

    public void edit(IRequestCycle cycle) {
        Object[] parameters = cycle.getListenerParameters();
        Long id = (Long) parameters[0];

        if (log.isDebugEnabled()) {
            log.debug("fetching user with id: " + id);
        }

        setUser(getUserManager().getUser(id.toString()));
        cycle.activate(this);
    }

    public ILink save(IRequestCycle cycle) {
        if (log.isDebugEnabled()) {
            log.debug("entered 'save' method");
        }

        if (getValidationDelegate().getHasErrors()) { 
            return null; 
        }
        
        getUserManager().saveUser(getUser());

        UserList nextPage = (UserList) cycle.getPage("users");
        nextPage.setMessage(getMessages().format("user.saved", getUser().getFullName()));
        // redirect to next page
        return getEngineService().getLink(false, nextPage.getPageName());
    }

    public ILink delete(IRequestCycle cycle) {
        if (log.isDebugEnabled()) {
            log.debug("entered 'delete' method");
        }

        getUserManager().removeUser(getUser().getId().toString());

        UserList nextPage = (UserList) cycle.getPage("users");
        nextPage.setMessage(getMessages().format("user.deleted", getUser().getFullName()));
        // redirect to next page
        return getEngineService().getLink(false, nextPage.getPageName());
    }
}
