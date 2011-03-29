package org.appfuse.webapp.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

public class Index extends WebPage {
    public Index() {
        add(new Link("usersLink") {
            @Override
            public void onClick() {
                setResponsePage(new UserList());
            }
        });
    }
}
