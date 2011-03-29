package org.appfuse.webapp.components;

import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ComponentDefaultProvider;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Binding;

/**
 * Displays message based on the type parameter
 *
 * @author Serge Eby
 * @version $Id: MessageBanner.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class MessageBanner {

    private static final String SUCCESS = "success";
    private static final String ERROR = "error";

    @Property
    @Parameter(required = true, allowNull = true)
    private String message;

    @Property
    @Parameter(required = false, allowNull = true)
    private String type = "success";

    @Inject
    private ComponentDefaultProvider defaultProvider;

    @Inject
    private Block error;

    @Inject
    private Block success;

    @Inject
    private ComponentResources resources;

    Binding defaultMessage() {
        return defaultProvider.defaultBinding("message", resources);
    }

    public Object getActiveBlock() {
        if (type == null) {
            return null;
        }
        if (!SUCCESS.equals(type) && !ERROR.equals(type)) {
            throw new RuntimeException("Invalid Message type: only 'error' or 'success' are supported");
        }
        return SUCCESS.equals(type) ? success : error;
    }

}
