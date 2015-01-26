package org.appfuse.webapp.pages;

import org.apache.wicket.markup.repeater.data.DataView;
import org.appfuse.service.UserManager;
import org.junit.Test;
import org.springframework.web.context.support.StaticWebApplicationContext;

import static org.mockito.Mockito.*;

public class UserListTest extends BasePageTest {
    @Override
    protected void initSpringBeans(StaticWebApplicationContext context) {
        super.initSpringBeans(context);
        context.getBeanFactory().registerSingleton("userManager", mock(UserManager.class));
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
