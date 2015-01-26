package org.appfuse.webapp;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.appfuse.webapp.pages.Index;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wicketstuff.annotation.scan.AnnotatedMountScanner;

@Component
public class Application extends WebApplication implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Override
    public void init() {
        super.init();
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, getContext(), true));
        configure();

        // remove <wicket:> tags in generated markup
        getMarkupSettings().setStripWicketTags(true);

        // scan for @MountPath annotations
        new AnnotatedMountScanner().scanPackage("org.appfuse.webapp.pages").mount(this);
    }

    public Class<Index> getHomePage() {
        return Index.class;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    protected ApplicationContext getContext() {
        return WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    }
}
