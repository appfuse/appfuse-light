package org.appfuse.web.rootmount;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.Page;
import org.apache.wicket.request.target.coding.BookmarkablePageRequestTargetUrlCodingStrategy;

/**
 * BookmarkablePageRequestTargetUrlCodingStrategy that mounts on the root.
 *
 * <p>An internal 'fake' mount path is used to do URL routing within Wicket. The internal mount path
 * is removed when the URL is encoded. It is therefore never visible outside the Wicket application.
 *
 * <p>Note that the original
 * {@link org.apache.wicket.request.target.coding.BookmarkablePageRequestTargetUrlCodingStrategy#encode(org.apache.wicket.IRequestTarget)}
 * is final and can therefore not be overridden here. Therefore, a patched version is needed.
 *
 * @author Erik van Oosten
 */
public abstract class RootMountedBookmarkablePageRequestTargetUrlCodingStrategy
        extends BookmarkablePageRequestTargetUrlCodingStrategy
        implements RootMountedUrlCodingStrategy {

    /**
     * Constructor.
     *
     * @param mountPath the internal 'fake' mount path, its exact value does not matter as long as
     *   it is a unique mount path in the entire application (not null)
     * @param bookmarkablePageClass type of target page (not null) See constructor of base class.
     * @param pageMapName the page map name or null for none
     * @param <P> type of target page
     */
    protected <P extends Page> RootMountedBookmarkablePageRequestTargetUrlCodingStrategy(
            String mountPath, Class<P> bookmarkablePageClass, String pageMapName) {

        super(mountPath, bookmarkablePageClass, pageMapName);
    }

    @Override
    public CharSequence encode(IRequestTarget requestTarget) {
        // Get the original URL (includes the internal mount path).
        String url = super.encode(requestTarget).toString();
        // Remove the internal mount path to directly mount on "/".
        // Note the +1 to also remove the '/'
        return url.substring(getMountPath().length() + 1);
    }

}