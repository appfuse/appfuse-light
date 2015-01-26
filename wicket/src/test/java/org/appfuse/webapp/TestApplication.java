package org.appfuse.webapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class TestApplication extends Application {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final ApplicationContext testContext;

    public TestApplication(ApplicationContext testContext) {
        this.testContext = testContext;
    }

    //ApplicationContext has to be created completely before Application class (cannot be created here)
    @Override
    protected ApplicationContext getContext() {
        return testContext;
    }

    @Override
    protected void outputDevelopmentModeWarning() {
        //it's ok for tests - no need to print large warning on stderr
        //just display info message to keep developer informed how many times web application is created in tests
        log.info("Starting test web application in development mode");
    }
}
