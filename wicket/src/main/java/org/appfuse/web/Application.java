package org.appfuse.web;

import org.appfuse.web.pages.Index;
import org.appfuse.web.pages.UserForm;
import org.appfuse.web.pages.UserList;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

public class Application extends WebApplication {
    public void init() {
        // notice in 2.0+ versions this is replaced by new SpringWicketModule(this);
        addComponentInstantiationListener(new SpringComponentInjector(this));
        configure(); 
        
        // Fixed SiteMesh: http://spatula.net/blog/2006/10/wicket-sitemesh-feces-nocturnus.html
        getRequestCycleSettings().setRenderStrategy(IRequestCycleSettings.ONE_PASS_RENDER);

        // remove <wicket:> tags in generated markup
        getMarkupSettings().setStripWicketTags(true);
        
        // make bookmarkable pages for easy linking from Menu/SiteMesh
        mountBookmarkablePage("/users", UserList.class);
        mountBookmarkablePage("/userform", UserForm.class);
    }

    public Class getHomePage() {
        return Index.class;
    }
}