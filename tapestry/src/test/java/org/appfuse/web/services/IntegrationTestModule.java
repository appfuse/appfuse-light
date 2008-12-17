package org.appfuse.web.services;

import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.OrderedConfiguration;

public class IntegrationTestModule {

    // contribution master object provider
    public static void contributeMasterObjectProvider(
            OrderedConfiguration<ObjectProvider> configuration,
            ObjectLocator locator) {
        configuration.add("spring", locator
                .autobuild(SpringObjectProvider.class));
    }
}
