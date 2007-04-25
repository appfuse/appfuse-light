package org.appfuse.web.pages;

import org.appfuse.service.UserManager;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.spring.injection.annot.SpringComponentInjector;
import wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import wicket.util.tester.WicketTester;

public class UserListTest extends AbstractDependencyInjectionSpringContextTests {
    private WicketTester tester;
    private UserManager userManager;
    
    protected String[] getConfigLocations() {
        return new String[] {"/WEB-INF/applicationContext*.xml"};
    }
    
    public void onSetUp() {
        tester = new WicketTester();
        AnnotApplicationContextMock context = new AnnotApplicationContextMock();
        userManager = (UserManager) applicationContext.getBean("userManager");
        context.putBean("userManager", userManager); 
        tester.addComponentInstantiationListener(new SpringComponentInjector(tester, context));
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
