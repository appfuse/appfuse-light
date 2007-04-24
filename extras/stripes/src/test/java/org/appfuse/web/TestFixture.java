package org.appfuse.web;

import net.sourceforge.stripes.mock.MockServletContext;
import net.sourceforge.stripes.mock.MockServletConfig;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.controller.DispatcherServlet;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.util.HashMap;
import java.util.Map;

public class TestFixture {
    private static MockServletContext context;

    public void init() {
        TestFixture.context = new MockServletContext("");
        context.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
                "classpath:/WEB-INF/applicationContext*.xml");

        ServletContextListener contextListener = new ContextLoaderListener();
        ServletContextEvent event = new ServletContextEvent(context);
        contextListener.contextInitialized(event);

        // Add the Stripes Filter
        Map<String, String> filterParams = new HashMap<String, String>();
        filterParams.put("ActionResolver.Class", "org.appfuse.web.ActionResolver");
        filterParams.put("ActionResolver.UrlFilters", "target/classes");
        filterParams.put("Interceptor.Classes", "net.sourceforge.stripes.integration.spring.SpringInterceptor,"
                + "net.sourceforge.stripes.controller.BeforeAfterMethodInterceptor");
        filterParams.put("LocalizationBundleFactory.ErrorMessageBundle", "messages");
        filterParams.put("LocalizationBundleFactory.FieldNameBundle", "messages");
        context.addFilter(StripesFilter.class, "StripesFilter", filterParams);

        // Add the Stripes Dispatcher
        context.setServlet(DispatcherServlet.class, "StripesDispatcher", null);

    }

    public MockServletContext getServletContext() {
        init();
        return TestFixture.context;
    }
}