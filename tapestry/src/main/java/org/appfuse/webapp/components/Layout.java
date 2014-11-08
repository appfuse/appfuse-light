package org.appfuse.webapp.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.services.Environment;


/**
 * Global Layout component
 *
 * @author Serge Eby
 * @version $Id: Layout.java 5 2008-08-30 09:59:21Z serge.eby $
 */

public class Layout {

    @Inject
    private Environment environment;

    @Inject
    private Context context;

    @Property
    @Parameter(required = true)
    private String title;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.MESSAGE)
    private String heading;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String menu;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String bodyId;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL)
    private String bodyClass;
}
