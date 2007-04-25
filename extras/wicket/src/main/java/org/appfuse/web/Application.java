package org.appfuse.web;

import org.appfuse.web.pages.Index;
import org.appfuse.web.pages.UserForm;
import org.appfuse.web.pages.UserList;

import wicket.protocol.http.WebApplication;
import wicket.settings.IRequestCycleSettings;
import wicket.spring.injection.annot.SpringComponentInjector;

public class Application extends WebApplication {
    public void init() {
        // notice in 2.0+ versions this is replaced by new SpringWicketModule(this);
        addComponentInstantiationListener(new SpringComponentInjector(this));
        configure(Application.DEPLOYMENT); // change this to development for better error messages
        
        // Fixed SiteMesh: http://spatula.net/blog/2006/10/wicket-sitemesh-feces-nocturnus.html
        getRequestCycleSettings().setRenderStrategy(IRequestCycleSettings.ONE_PASS_RENDER);
        
        // make bookmarkable pages for easy linking from Menu/SiteMesh
        mountBookmarkablePage("/users", UserList.class);
        mountBookmarkablePage("/userform", UserForm.class);
    }

    public Class getHomePage() {
        return Index.class;
    }
}