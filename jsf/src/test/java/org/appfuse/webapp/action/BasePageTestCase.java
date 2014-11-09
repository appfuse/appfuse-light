package org.appfuse.webapp.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shale.test.mock.*;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIViewRoot;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.render.RenderKitFactory;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <p>Abstract JUnit test case base class, which sets up the JavaServer Faces
 * mock object environment for a particular simulated request.  The following
 * protected variables are initialized in the <code>setUp()</code> method, and
 * cleaned up in the <code>tearDown()</code> method:</p>
 * <ul>
 * <li><code>application</code> (<code>MockApplication</code>)</li>
 * <li><code>config</code> (<code>MockServletConfig</code>)</li>
 * <li><code>externalContext</code> (<code>MockExternalContext</code>)</li>
 * <li><code>facesContext</code> (<code>MockFacesContext</code>)</li>
 * <li><code>lifecycle</code> (<code>MockLifecycle</code>)</li>
 * <li><code>request</code> (<code>MockHttpServletRequest</code></li>
 * <li><code>response</code> (<code>MockHttpServletResponse</code>)</li>
 * <li><code>servletContext</code> (<code>MockServletContext</code>)</li>
 * <li><code>session</code> (<code>MockHttpSession</code>)</li>
 * </ul>
 * <p/>
 * <p>In addition, appropriate factory classes will have been registered with
 * <code>javax.faces.FactoryFinder</code> for <code>Application</code> and
 * <code>RenderKit</code> instances.  The created <code>FacesContext</code>
 * instance will also have been registered in the apppriate thread local
 * variable, to simulate what a servlet container would do.</p>
 * <p/>
 * <p><strong>WARNING</strong> - If you choose to subclass this class, be sure
 * your <code>onSetUp()</code> and <code>onTearDown()</code> methods call
 * <code>super.setUp()</code> and <code>super.tearDown()</code> respectively,
 * and that you implement your own <code>suite()</code> method that exposes
 * the test methods for your test case.</p>
 * <p/>
 * <p><strong>NOTE:</strong> This class is a copy of Shale's AbstractJsfTestCase,
 * except it extends Spring's AbstractTransactionalDataSourceSpringContextTests
 * instead of JUnit's TestCase.</p>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations = {"classpath:/applicationContext-resources.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-service.xml",
        "classpath*:/applicationContext.xml",
        "classpath:**/applicationContext*.xml"})
public abstract class BasePageTestCase {
    protected final Log log = LogFactory.getLog(getClass());

    protected MockApplication application = null;
    protected MockServletConfig config = null;
    protected MockExternalContext externalContext = null;
    protected MockFacesContext facesContext = null;
    protected MockFacesContextFactory facesContextFactory = null;
    protected MockLifecycle lifecycle = null;
    protected MockLifecycleFactory lifecycleFactory = null;
    protected MockRenderKit renderKit = null;
    protected MockHttpServletRequest request = null;
    protected MockHttpServletResponse response = null;
    protected MockServletContext servletContext = null;
    protected MockHttpSession session = null;

    // Thread context class loader saved and restored after each test
    private ClassLoader threadContextClassLoader = null;

    /**
     * <p>Set up instance variables required by this test case.</p>
     */
    @Before
    public void onSetUp() {
        // Set up a new thread context class loader
        threadContextClassLoader = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(new URLClassLoader(new URL[0],
            this.getClass().getClassLoader()));

        // Set up Servlet API Objects
        servletContext = new MockServletContext();

        config = new MockServletConfig(servletContext);
        session = new MockHttpSession();
        session.setServletContext(servletContext);
        request = new MockHttpServletRequest(session);
        request.setServletContext(servletContext);
        request.setLocale(new Locale("en"));
        response = new MockHttpServletResponse();

        // Set up JSF API Objects
        FactoryFinder.releaseFactories();
        FactoryFinder.setFactory(FactoryFinder.APPLICATION_FACTORY,
            "org.apache.shale.test.mock.MockApplicationFactory");
        FactoryFinder.setFactory(FactoryFinder.FACES_CONTEXT_FACTORY,
            "org.apache.shale.test.mock.MockFacesContextFactory");
        FactoryFinder.setFactory(FactoryFinder.LIFECYCLE_FACTORY,
            "org.apache.shale.test.mock.MockLifecycleFactory");
        FactoryFinder.setFactory(FactoryFinder.RENDER_KIT_FACTORY,
            "org.apache.shale.test.mock.MockRenderKitFactory");

        externalContext = new MockExternalContext(servletContext, request, response);
        lifecycleFactory = (MockLifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        lifecycle = (MockLifecycle) lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
        facesContextFactory = (MockFacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        facesContext = (MockFacesContext)
            facesContextFactory.getFacesContext(servletContext, request, response, lifecycle);

        externalContext = (MockExternalContext) facesContext.getExternalContext();
        UIViewRoot root = new UIViewRoot();
        root.setViewId("/viewId");
        root.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
        facesContext.setViewRoot(root);
        ApplicationFactory applicationFactory = (ApplicationFactory)
            FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        application = (MockApplication) applicationFactory.getApplication();
        facesContext.setApplication(application);
        RenderKitFactory renderKitFactory = (RenderKitFactory)
            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        renderKit = new MockRenderKit();
        renderKitFactory.addRenderKit(RenderKitFactory.HTML_BASIC_RENDER_KIT, renderKit);

        application.setMessageBundle("messages");

        // set supported locales
        List<Locale> list = new ArrayList<Locale>();
        list.add(new Locale("en"));
        list.add(new Locale("es"));
        application.setSupportedLocales(list);
    }

    /**
     * <p>Tear down instance variables required by this test case.</p>
     */
    @After
    public void onTearDown() {
        application = null;
        config = null;
        externalContext = null;
        facesContext.release();
        facesContext = null;
        lifecycle = null;
        lifecycleFactory = null;
        renderKit = null;
        request = null;
        response = null;
        servletContext = null;
        session = null;
        FactoryFinder.releaseFactories();

        Thread.currentThread().setContextClassLoader(threadContextClassLoader);
        threadContextClassLoader = null;
    }
}
