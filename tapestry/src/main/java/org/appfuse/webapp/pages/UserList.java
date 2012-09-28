package org.appfuse.webapp.pages;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.slf4j.Logger;

import java.util.List;

public class UserList {
    @Inject
    private Logger log;

    @Inject
    private UserManager userManager;

    @Property
    private User currentUser;

    @Inject
    private PageRenderLinkSource pageRenderLinkSource;


    public List<User> getUsers() {
        return userManager.getAll();
    }

    Link onAdd() {
        return pageRenderLinkSource.createPageRenderLinkWithContext(UserForm.class);
    }

    Object onActionFromEdit(User user) {
        log.debug("fetching user with id: " + user.getId());
        return pageRenderLinkSource.createPageRenderLinkWithContext(UserForm.class, user);
    }
}
