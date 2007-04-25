package org.appfuse.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import servletunit.struts.MockStrutsTestCase;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public abstract class BaseStrutsTestCase extends MockStrutsTestCase {
    protected final Log log = LogFactory.getLog(getClass());
    protected static ApplicationContext ctx = null;

    public BaseStrutsTestCase(String name) {
        super(name);
    }

    protected void setUp() throws Exception {
        super.setUp();

        // initialize Spring for backend beans
        MockServletContext sc = new MockServletContext("");
        sc.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
                "/WEB-INF/applicationContext*.xml");
        
        ServletContextListener contextListener = new ContextLoaderListener();
        ServletContextEvent event = new ServletContextEvent(sc);
        contextListener.contextInitialized(event);
        
        // magic bridge to make StrutsTestCase aware of Spring's Context
        getSession().getServletContext().setAttribute(
                WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, 
                sc.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
        
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                    getSession().getServletContext());
    }
}
