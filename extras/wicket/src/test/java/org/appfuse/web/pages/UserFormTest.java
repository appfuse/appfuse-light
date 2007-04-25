package org.appfuse.web.pages;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import wicket.Page;
import wicket.markup.html.form.Form;
import wicket.spring.injection.annot.SpringComponentInjector;
import wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import wicket.util.tester.ITestPageSource;
import wicket.util.tester.WicketTester;

public class UserFormTest extends AbstractDependencyInjectionSpringContextTests {
    private WicketTester tester;

    private UserManager userManager;

    protected String[] getConfigLocations() {
        return new String[] { "/WEB-INF/applicationContext*.xml" };
    }

    public void onSetUp() {
        tester = new WicketTester();
        AnnotApplicationContextMock context = new AnnotApplicationContextMock();
        userManager = (UserManager) applicationContext.getBean("userManager");
        context.putBean("userManager", userManager);
        tester.addComponentInstantiationListener(new SpringComponentInjector(tester, context));
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
