package org.appfuse.webapp.pages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.internal.spring.SpringModuleDef;
import org.apache.tapestry5.ioc.def.ModuleDef;
import org.apache.tapestry5.test.PageTester;
import org.appfuse.Constants;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {
        "classpath:/applicationContext-resources.xml", "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-service.xml", "classpath*:/applicationContext.xml",
        "/WEB-INF/applicationContext*.xml"})
public abstract class BasePageTestCase extends AbstractTransactionalJUnit4SpringContextTests {
    protected PageTester tester;
    protected Document doc;
    protected Map<String, String> fieldValues;
    protected final Log log = LogFactory.getLog(getClass());
    protected static final String MESSAGES = "messages";

    @Before
    public void onSetUp() {
        String appPackage = "org.appfuse.webapp";
        String appName = "app";
        tester = new PageTester(appPackage, appName, "src/main/webapp") {
            @Override
            protected ModuleDef[] provideExtraModuleDefs() {
                return new ModuleDef[]{new SpringModuleDef(applicationContext)};
            }
        };

        fieldValues = new HashMap<String, String>();
    }

    @After
    public void onTearDown() {
        if (tester != null) {
            tester.shutdown();
        }
        tester = null;
    }
}