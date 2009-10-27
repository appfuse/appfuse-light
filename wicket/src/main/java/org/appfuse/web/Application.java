package org.appfuse.web;

import org.appfuse.web.pages.Index;
import org.appfuse.web.pages.UserForm;
import org.appfuse.web.pages.UserList;

import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;

public class Application extends WebApplication {
    @Override
    public void init() {
        super.init();
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

    public Class<Index> getHomePage() {
        return Index.class;
    }
}