package org.appfuse.webapp.pages;

import org.apache.wicket.Page;
import org.appfuse.webapp.rootmount.RootMountedBookmarkablePageRequestTargetUrlCodingStrategy;

/**
 * Url Strategy for allowing any page to be mounted as at the root context.
 */
public class DefaultUrlCodingStrategy extends RootMountedBookmarkablePageRequestTargetUrlCodingStrategy {

    /**
     * Constructor.
     *
     * @param mountPath the internal 'fake' mount path, its exact value does not matter as long as
     *   it is a unique mount path in the entire application (not null)
     * @param bookmarkablePageClass type of target page (not null) See constructor of base class.
     * @param <P> type of target page
     */
    public <P extends Page> DefaultUrlCodingStrategy(String mountPath, Class<P> bookmarkablePageClass) {
        super(mountPath, bookmarkablePageClass, null);
    }

    public boolean accepts(String rawPath) {
        return true;
    }
}
