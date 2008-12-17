package org.appfuse.web.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import java.util.List;

/**
 * Main index page. This also handles 404 errors
 *
 * @author Serge Eby
 * @version $Id: Index.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class Index {
    @Inject
    private Logger log;

    @SuppressWarnings("unchecked")
    private List eventContext;

    @SuppressWarnings("unchecked")
    public List getEventContext() {
        return eventContext;
    }

    Object onActivate(List context) {
        eventContext = context;
        if (context != null && !context.isEmpty()) {
            int index = 0;
            for (Object obj : context) {
                index++;
                log.debug(String.format("Context #%d =  %s", index, obj.toString()));
            }
            log.debug("Redirecting to PageNotFound");
            return NotFound.class;
        }
        return Home.class;
    }
}
