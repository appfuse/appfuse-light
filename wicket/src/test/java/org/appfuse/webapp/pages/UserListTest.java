package org.appfuse.webapp.pages;

import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.appfuse.service.UserManager;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class UserListTest {
    private WicketTester tester;

    @Before
    public void onSetUp() {
        tester = new WicketTester();
        ApplicationContextMock applicationContextMock = new ApplicationContextMock();
        applicationContextMock.putBean("userManager", mock(UserManager.class));
        tester.getApplication().getComponentInstantiationListeners().add(
            new SpringComponentInjector(tester.getApplication(), applicationContextMock));
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
