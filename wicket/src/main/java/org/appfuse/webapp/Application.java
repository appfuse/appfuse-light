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

        // remove <wicket:> tags in generated markup
        getMarkupSettings().setStripWicketTags(true);

        // make bookmarkable pages for easy linking from Menu/SiteMesh
        mountPage("/users", UserList.class);
        mountPage("/userform", UserForm.class);
        // add additional pages here
    }

    public Class<Index> getHomePage() {
        return Index.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
