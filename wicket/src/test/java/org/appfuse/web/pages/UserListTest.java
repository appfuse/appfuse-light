package org.appfuse.web.pages;

import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.appfuse.service.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration({"classpath:/applicationContext-resources.xml", "classpath:/applicationContext-dao.xml",
            "classpath:/applicationContext-service.xml", "/WEB-INF/applicationContext*.xml"})
public class UserListTest extends AbstractJUnit4SpringContextTests {
    private WicketTester tester;

    @Before
    public void onSetUp() {
        tester = new WicketTester();
        AnnotApplicationContextMock context = new AnnotApplicationContextMock();
        UserManager userManager = (UserManager) applicationContext.getBean("userManager");
        context.putBean("userManager", userManager);
        tester.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(tester.getApplication(), context, true));
    }

    @Test
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
