package org.appfuse.web.services;

import org.apache.tapestry5.ioc.ServiceBinder;
import org.appfuse.service.UserManager;

public class MockTestModule {

    // http://www.nabble.com/-T5--Testing-Pages-with-injected-Spring-beans-tt21057429.html#a21058779
    public static void bind(final ServiceBinder binder) {
        binder.bind(UserManager.class, MockUserManagerImpl.class);
    }
}
