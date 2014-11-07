package org.appfuse.webapp;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.appfuse.webapp.pages.Index;
import org.appfuse.webapp.pages.UserForm;
import org.appfuse.webapp.pages.UserList;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Application extends WebApplication implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx, true));
        configure();

        // Fixed SiteMesh: http://spatula.net/blog/2006/10/wicket-sitemesh-feces-nocturnus.html
        //getRequestCycleSettings().setRenderStrategy(IRequestCycleSettings.ONE_PASS_RENDER);

        // remove <wicket:> tags in generated markup
        getMarkupSettings().setStripWicketTags(true);

        // make bookmarkable pages for easy linking from Menu/SiteMesh
        mountPage("/users", UserList.class);
        mountPage("/userform", UserForm.class);
    }

    public Class<Index> getHomePage() {
        return Index.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
