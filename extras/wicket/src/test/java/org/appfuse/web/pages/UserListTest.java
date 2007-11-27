package org.appfuse.web.pages;

import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.appfuse.service.UserManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class UserListTest extends AbstractDependencyInjectionSpringContextTests {
    private WicketTester tester;

    protected String[] getConfigLocations() {
        return new String[] {"/WEB-INF/applicationContext*.xml"};
    }
    
    public void onSetUp() {
        tester = new WicketTester();
        AnnotApplicationContextMock context = new AnnotApplicationContextMock();
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");
        context.putBean("userManager", userManager);
        tester.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(tester.getApplication(), context));
    }

    public void testRenderPage() {
        tester.startPage(UserList.class);
        
        // check that the right page was rendered (no unexpected redirect or intercept)
        tester.assertRenderedPage(UserList.class);
        // assert that there's no error message
        tester.assertNoErrorMessage();
        // check that the right components are in the page
        tester.assertComponent("users", DataView.class);
    }
}
