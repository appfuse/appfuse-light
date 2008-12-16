package org.appfuse.web.pages;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.IPageLink;
import org.apache.wicket.markup.html.link.PageLink;

public class Index extends WebPage {
    public Index() {        
        add(new PageLink("usersLink", new IPageLink() {
            public Page getPage() {
                return new UserList();
            }
            public Class getPageIdentity() {
                return UserList.class;
            }
        }));
    }
}
