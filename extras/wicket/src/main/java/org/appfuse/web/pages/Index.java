package org.appfuse.web.pages;

import wicket.Page;
import wicket.markup.html.WebPage;
import wicket.markup.html.link.IPageLink;
import wicket.markup.html.link.PageLink;

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
