package org.appfuse.web;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.el.ValueBinding;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import junit.framework.TestCase;
import org.apache.myfaces.webapp.StartupServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public abstract class BasePageTestCase extends TestCase {
    protected final Log log = LogFactory.getLog(getClass());
    protected static FacesContext facesContext;
    protected static MockServletConfig config;
    protected static MockServletContext servletContext;
    protected static WebApplicationContext ctx;

    // This static block ensures that Spring's BeanFactory and JSF's 
    // FacesContext is only loaded once for all tests. 
    static {
        servletContext = new MockServletContext("");
        // This static block ensures that Spring's BeanFactory and JSF's 
        // FacesContext is only loaded once for all tests. 
        servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
                                        "/WEB-INF/applicationContext*.xml");

        ServletContextListener contextListener = new ContextLoaderListener();
        ServletContextEvent event = new ServletContextEvent(servletContext);
        contextListener.contextInitialized(event);

        config = new MockServletConfig(servletContext);
        facesContext = performFacesContextConfig();
        ctx = FacesContextUtils.getRequiredWebApplicationContext(facesContext);
    }
    
    protected static FacesContext performFacesContextConfig() {
        StartupServletContextListener facesListener =
            new StartupServletContextListener();
        ServletContextEvent event = new ServletContextEvent(servletContext);
        facesListener.contextInitialized(event);

        LifecycleFactory lifecycleFactory =
            (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);

        Lifecycle lifecycle = lifecycleFactory.getLifecycle(getLifecycleId());

        FacesContextFactory facesCtxFactory =
            (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

        FacesContext ctx =
            facesCtxFactory.getFacesContext(servletContext,
                                            new MockHttpServletRequest(),
                                            new MockHttpServletResponse(),
                                            lifecycle);

        return ctx;
    }

    protected static String getLifecycleId() {
        String lifecycleId =
            servletContext.getInitParameter(FacesServlet.LIFECYCLE_ID_ATTR);

        return (lifecycleId != null) ? lifecycleId
                                     : LifecycleFactory.DEFAULT_LIFECYCLE;
    }

    /**
     * Get managed bean based on the bean name.
     *
     * @param beanName the bean name
     * @return the managed bean associated with the bean name
     */
    protected Object getManagedBean(String beanName) {
        return getValueBinding(getJsfEl(beanName)).getValue(facesContext);
    }

    private Application getApplication() {
        ApplicationFactory appFactory =
            (ApplicationFactory) FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);

        return appFactory.getApplication();
    }

    private ValueBinding getValueBinding(String el) {
        return getApplication().createValueBinding(el);
    }

    private String getJsfEl(String value) {
        return "#{" + value + "}";
    }
}
