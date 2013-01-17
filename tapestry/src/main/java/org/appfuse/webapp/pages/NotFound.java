package org.appfuse.webapp.pages;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.appfuse.webapp.util.MessageUtil;

/**
 * Page to be displayed whenever a page is not found (404 error)
 *
 * @author Serge Eby
 * @version $Id: NotFound.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class NotFound {

    @Inject
    private Messages messages;

    @Inject
    private PageRenderLinkSource renderLinkSource;

    public String getNotFoundMessage() {
        String message = MessageUtil.convert(messages.get("404.message"));
        String url = renderLinkSource.createPageRenderLink(Index.class).toAbsoluteURI();
        return String.format(message, url);
    }
}
