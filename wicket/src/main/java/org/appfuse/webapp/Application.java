package org.appfuse.webapp;

import org.apache.wicket.request.IRequestCycleProcessor;
import org.appfuse.webapp.pages.DefaultUrlCodingStrategy;
import org.appfuse.webapp.pages.Index;
import org.appfuse.webapp.pages.UserForm;
import org.appfuse.webapp.pages.UserList;

import org.apache.wicket.settings.IRequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.protocol.http.WebApplication;
import org.appfuse.webapp.rootmount.RootMountedUrlCodingStrategy;
import org.appfuse.webapp.rootmount.RootWebRequestProcessor;

import java.util.ArrayList;
import java.util.List;

public class Application extends WebApplication {

    private List<RootMountedUrlCodingStrategy> rootMounts = new ArrayList<RootMountedUrlCodingStrategy>();

    /**
     * Constructor
     */
    public Application() {
        // NOTE: there is no need to synchronize this list as it is written to from the init() method.
        rootMounts = new ArrayList<RootMountedUrlCodingStrategy>(10);
    }

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
        //mountBookmarkablePage("/index", Index.class);
        mountOnRoot(new DefaultUrlCodingStrategy("/users", UserList.class));
        mountOnRoot(new DefaultUrlCodingStrategy("/userform", UserForm.class));
    }

    public Class<Index> getHomePage() {
        return Index.class;
    }

      /**
     * Mount a target url strategy no the root URL.
     *
     * @param strategy the strategy (not null)
     */
    private void mountOnRoot(RootMountedUrlCodingStrategy strategy) {
        rootMounts.add(strategy);

        // Also add to the wicket mounts so that it can be found when searched by target page class.
        mount(strategy);
    }

    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {

        return new RootWebRequestProcessor(rootMounts);

        // Alternative that redirects all unknown requests to the not found page.
        // Please read {@link RootWebRequestProcessor class comment} before you enable this.
        //
        // return new RootWebRequestProcessor(rootMounts, NotFoundPage.class);
    }
}