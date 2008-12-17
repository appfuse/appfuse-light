package org.appfuse.web.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.slf4j.Logger;

import java.io.IOException;


/**
 * Application global configurations
 *
 * @author Serge Eby
 * @version $Id: AppModule.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class AppModule {
    public static void bind(ServiceBinder binder) {
        // binder.bind(MyServiceInterface.class, MyServiceImpl.class);

        // Make bind() calls on the binder object to define most IoC
        // services.
        // Use service builder methods (example below) when the
        // implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.
    }

    /**
     * Decorate Error page
     *
     * @param logger
     * @param renderer
     * @param componentSource
     * @param productionMode
     * @param service
     * @return
     */
    public RequestExceptionHandler decorateRequestExceptionHandler(
            final Logger logger,
            final ResponseRenderer renderer,
            final ComponentSource componentSource,
            @Symbol(SymbolConstants.PRODUCTION_MODE)
            boolean productionMode,
            Object service) {
        if (!productionMode) {
            return null;
        }

        return new RequestExceptionHandler() {
            public void handleRequestException(Throwable exception)
                    throws IOException {
                logger.error("Unexpected runtime exception: " + exception.getMessage(), exception);
                ExceptionReporter error = (ExceptionReporter) componentSource.getPage("Error");
                error.reportException(exception);
                renderer.renderPageMarkupResponse("Error");
            }
        };
    }
}