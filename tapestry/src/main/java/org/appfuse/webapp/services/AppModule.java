package org.appfuse.webapp.services;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.services.ComponentSource;
import org.apache.tapestry5.services.ExceptionReporter;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.ResponseRenderer;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.apache.tapestry5.services.ValueEncoderFactory;
import org.apache.tapestry5.ValueEncoder;
import org.slf4j.Logger;

import java.io.IOException;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.appfuse.webapp.services.impl.UserEncoder;



/**
 * Application global configurations
 *
 * @author Serge Eby
 * @version $Id: AppModule.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class AppModule {

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
        configuration.add(SymbolConstants.GZIP_COMPRESSION_ENABLED, "false");
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



    @Contribute(ValueEncoderSource.class)
    public static void provideEncoders(
            MappedConfiguration<Class, ValueEncoderFactory> configuration,
            UserManager userManager) {

        contributeEncoder(configuration, User.class, new UserEncoder(userManager));

    }

    private static <T> void contributeEncoder(MappedConfiguration<Class, ValueEncoderFactory> configuration,
                                              Class<T> clazz, final ValueEncoder<T> encoder) {

        ValueEncoderFactory<T> factory = new ValueEncoderFactory<T>() {

            public ValueEncoder<T> create(Class<T> clazz) {
                return encoder;
            }
        };

        configuration.add(clazz, factory);
    }
}
