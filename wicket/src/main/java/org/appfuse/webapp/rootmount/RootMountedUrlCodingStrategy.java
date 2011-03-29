package org.appfuse.webapp.rootmount;

import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;

/**
 * A TargetUrlCodingStrategy that can be mounted on the root.
 *
 * <p>Every implementation of RootMountedUrlCodingStrategy MUST
 * remove the mount path from the URL upon an encode.</p>
 *
 * @author Erik van Oosten
 */
public interface RootMountedUrlCodingStrategy extends IRequestTargetUrlCodingStrategy {

    /**
     * Determine whether the path is accepted by this URL coding strategy.
     * <p>
     * Note: for each request that is not handled by the regular wicket mounts, this method is
     * called twice. Result caching is left to the implementation.
     * <p>
     * Note 2: there is no guarantee that this method is only invoked twice per request, there might be more.
     * <p>
     * Note 3: during the first invocation in a request,
     * {@link org.apache.wicket.Application#get() Application.get()},
     * {@link org.apache.wicket.Session#get() Session.get()} and
     * {@link org.apache.wicket.RequestCycle#get() RequestCycle.get()} return null.
     *
     * @param rawPath the complete raw path (could be null or empty)
     *   (See also {@link RawPathUtil}.)
     * @return true when the path is accepted by this URL coding strategy, false otherwise
     */
    boolean accepts(String rawPath);

}
