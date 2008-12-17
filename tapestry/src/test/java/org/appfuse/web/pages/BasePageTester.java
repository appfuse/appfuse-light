package org.appfuse.web.pages;

import org.apache.tapestry5.test.PageTester;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.appfuse.web.services.IntegrationTestModule;
import org.appfuse.web.services.MockTestModule;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;

import java.util.Map;
import java.util.HashMap;

public abstract class BasePageTester extends Assert {
    protected PageTester tester;
    protected Document doc;
    protected Map<String, String> fieldValues;
    protected String testType;
    protected final static String MOCK = "mock";
    protected final static String INTEGRATION = "integration";

    @Before
    public void before() {
        String appPackage = "org.appfuse.web";
        String appName = "app";
        tester = new PageTester(appPackage, appName, "src/main/webapp", MockTestModule.class);
        testType = MOCK;
        fieldValues = new HashMap<String, String>();
    }

    @After
    public void after() {
        if (tester != null) {
            tester.shutdown();
        }

    }

    protected Element populateForm() {
        doc = tester.renderPage("UserForm");
        Element form = doc.getElementById("userForm");
        assertNotNull(form);

        fieldValues.put("username", "tapestry");
        fieldValues.put("password", "isfun");
        fieldValues.put("firstName", "Tapestry");
        fieldValues.put("lastName", "5");
        fieldValues.put("email", "tapestry@appfuse.org");

        doc = tester.submitForm(form, fieldValues);
        return form;
    }
}