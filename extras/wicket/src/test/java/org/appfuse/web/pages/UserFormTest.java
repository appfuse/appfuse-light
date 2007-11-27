package org.appfuse.web.pages;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.ITestPageSource;
import org.apache.wicket.util.tester.WicketTester;

public class UserFormTest extends AbstractDependencyInjectionSpringContextTests {
    private WicketTester tester;

    protected String[] getConfigLocations() {
        return new String[] { "/WEB-INF/applicationContext*.xml" };
    }

    public void onSetUp() {
        tester = new WicketTester();
        AnnotApplicationContextMock context = new AnnotApplicationContextMock();
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");
        context.putBean("userManager", userManager);
        tester.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(tester.getApplication(), context));
    }

    public void testAdd() {
        tester.startPage(new ITestPageSource() {
            public Page getTestPage() {
                return new UserForm(null, new User());
            }
        });

        tester.assertRenderedPage(UserForm.class);
        tester.assertNoErrorMessage();
        tester.assertComponent("user-form", Form.class);

        tester.submitForm("user-form");
        //tester.assertErrorMessages(new String[] { "Last name is required." });
    }
}
