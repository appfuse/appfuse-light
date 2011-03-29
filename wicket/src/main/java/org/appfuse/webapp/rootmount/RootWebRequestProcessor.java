package org.appfuse.webapp.rootmount;

import org.apache.wicket.Page;
import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.request.urlcompressing.UrlCompressingWebCodingStrategy;
import org.apache.wicket.protocol.http.request.urlcompressing.UrlCompressingWebRequestProcessor;
import org.apache.wicket.request.IRequestCodingStrategy;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.util.string.Strings;

import java.util.List;

/**
 * Url coding webapp request processor to catch parameters directly on the root mount path.
 * For example used to support URLs of the form http://www.example.com/member.
 *
 * <p>To use root mounts, make sure your application overrides
 * {@link org.apache.wicket.protocol.http.WebApplication#newRequestCycleProcessor}
 * and returns an instance of this. Secondly, these root mounts <strong>MUST</strong> be mounted directly via
 * {@link org.apache.wicket.protocol.http.WebApplication#mount(org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy)}.
 * For example use {@link org.appfuse.webapp.Application#mountOnRoot(RootMountedUrlCodingStrategy)}.
 *
 * <p>In addition you may want to configure ignorePaths as described below.
 *
 * <p>The following request handlers are considered in order:
 * <ol>
 * <li>As in Wicket's normal request handling: all normal page mounts (created with the
 *   WebApplication#mount* methods), mounted resources, call backs to Link's, ajax requests, etc.
 * <li>Pages mounted as given to the constructor of this. They are tried in the order as given.
 * <li>The not-found page (when not null).
 * <li>Fall back to the servlet container to handle non-Wicket content (this includes servlets configured
 *   in webapp.xml and all static content in the WEB-INF folder).
 * </ol>
 *
 * <p>To speed up URL processing, it is <strong>RECOMMENDED</strong> to move all non-Wicket content (i.e. servlets
 * and static content in the WEB-INF directory) behind a clear URL path and let the Wicket filter ignore these URLs.
 * You <strong>MUST</strong> do this if you want to combine non-Wicket content with the optional not-found page.
 *
 * <p>Here is an example fragment of the <code>ignorePath</code> parameter that makes the Wicket filter ignore all
 * request for <code>/favicon.ico</code>, <code>/robots.txt</code> and requests of which the path starts with
 * <code>/static/</code> and <code>/servletpath/</code>:
 * <pre>
 * &lt;!-- The Wicket application filter. --&gt;
 * &lt;filter&gt;
 *   &lt;filter-name&gt;wicket.filter&lt;/filter-name&gt;
 *   &lt;filter-class&gt;org.apache.wicket.protocol.http.WicketFilter&lt;/filter-class&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;applicationFactoryClassName&lt;/param-name&gt;
 *     &lt;param-value&gt;org.apache.wicket.spring.SpringWebApplicationFactory&lt;/param-value&gt;
 *   &lt;/init-param&gt;
 *   &lt;init-param&gt;
 *     &lt;param-name&gt;ignorePaths&lt;/param-name&gt;
 *     &lt;param-value&gt;favicon.ico,robots.txt,static/,servletpath/&lt;/param-value&gt;
 *   &lt;/init-param&gt;
 * &lt;/filter&gt;
 * </pre>
 * You can find this XML in your WEB.xml. (The example XML above uses Spring to instantiate the application class.)
 *
 * <p>See class comment of {@link org.apache.wicket.protocol.http.request.urlcompressing.UrlCompressingWebRequestProcessor} for more information on the
 * purpose of the base class. Alternatively, this could extend
 * {@link org.apache.wicket.protocol.http.WebRequestCycleProcessor}.
 *
 * @author Erik van Oosten
 */
public class RootWebRequestProcessor extends UrlCompressingWebRequestProcessor {

    private List<RootMountedUrlCodingStrategy> rootMounts;
    private Class<? extends Page> notFoundPage;

    /**
     * Constructor.
     *
     * @param rootMounts a list of root mounts in the order in which they are tried (not null)
     *   The list must be immutable or an implementation of something like
     *   {@link java.util.concurrent.CopyOnWriteArrayList}.
     */
    public <P extends Page> RootWebRequestProcessor(List<RootMountedUrlCodingStrategy> rootMounts) {
        this(rootMounts, null);
    }

    /**
     * Constructor.
     *
     * @param rootMounts a list of root mounts in the order in which they are tried (not null)
     *   The list must be immutable or an implementation of something like
     *   {@link java.util.concurrent.CopyOnWriteArrayList}.
     * @param notFoundPage the page to forward to when none of the root mounts accepts the current request, or null
     *   to fall through to the servlet container. When this value is non-null, and you have non-wicket content,
     *   you <strong>MUST</strong> configure ignorePaths in the webapp.xml as described in the
     *   {@link org.appfuse.webapp.rootmount.RootWebRequestProcessor class comment} (for performance this is recommended anyway).
     */
    public <P extends Page> RootWebRequestProcessor(List<RootMountedUrlCodingStrategy> rootMounts, Class<P> notFoundPage) {
        this.rootMounts = rootMounts;
        this.notFoundPage = notFoundPage;
    }

    @Override
    protected IRequestCodingStrategy newRequestCodingStrategy() {
        return new FallbackUrlRequestCodingStrategy();
    }

    /**
     * Url coding webapp strategy to catch parameters directly on the root mount path.
     * Forwards all unmounted requests to the FallbackUrlPage.
     */
    private class FallbackUrlRequestCodingStrategy extends UrlCompressingWebCodingStrategy {

        /**
         * During a simple request cycle this method is invoked twice:
         * 1. by Wicket's filter to determine whether we have a request that needs
         *    to be handled by Wicket at all (returning a non-null value is sufficient)
         *    (This step is skipped for resources and the home page.)
         * 2. by Wicket's request handling (from WebRequestCycle) to actually start
         *    processing the request. In this second invocation the 'fake' mount path
         *    is present in the path argument, therefore Wicket is able to get the
         *    correct url coding strategy (in the call to the super class), and there
         *    is no need to call {@link #findRootMount(String)} again.
         * <p>
         * This is also invoked during form processing and from mock requests (WicketTester).
         *
         * @param path the relative path (the requested uri, minus query string,
         *  context path, and filterPath. Relative, no leading '/'.
         * @return the url coding strategy to use or null when this is not a Wicket request
         */
        @Override
        public IRequestTargetUrlCodingStrategy urlCodingStrategyForPath(String path) {
            IRequestTargetUrlCodingStrategy strategy = super.urlCodingStrategyForPath(path);

            // Wicket could not find a (mounted) target for the given path, lets see if it is
            // mounted on the root path.
            if (strategy == null && !Strings.isEmpty(path)) {

                // Determine the target and return the URL coding strategy (if any)
                RootMountedUrlCodingStrategy rootStrategy = findRootMount(path);

                if (rootStrategy == null) {
                    if (notFoundPage != null) {
                        // Forward to the not found page
                        strategy = new QueryStringUrlCodingStrategy("", notFoundPage);
                    }
                } else {
                    strategy = rootStrategy;
                }
            }

            return strategy;
        }

        /**
         * Note that method may be invoked by Wicket for many different cases. Often however, the
         * number of invocations is limited to once per request.
         *
         * @param request the request (not null)
         * @return the path for this request (not null)
         */
        @Override
        protected String getRequestPath(Request request) {
            // Get the real path of the request.
            String path = request.getPath();

            // Empty path is reserved for home page;
            // no need to see if it is a root mounted path.
            if (!Strings.isEmpty(path)) {
                // Recompute the regular wicket url coding strategy
                IRequestTargetUrlCodingStrategy wicketStrategy =
                        super.urlCodingStrategyForPath(path);
                // Is this a regular Wicket path? If so, just leave it alone.
                if (wicketStrategy == null) {

                    // If not, it might be a root path.
                    RootMountedUrlCodingStrategy strategy = findRootMount(path);
                    if (strategy != null) {
                        // It is a root path; add the internal 'fake' mount path
                        // so that Wicket doesn't get confused.
                        path = strategy.getMountPath() + "/" + path;
                    }
                }
            }

            return path;
        }
    }

    /**
     * Iterate over the root mounted url coding strategies to see if any accepts this path.
     *
     * @param path the relative path (the requested uri, minus query string,
     *  context path, and filterPath. Relative, no leading '/'.
     * @return the first root mounted url coding strategies that accepts this path, or null when there are none
     */
    private RootMountedUrlCodingStrategy findRootMount(String path) {
        for (RootMountedUrlCodingStrategy rootMount : rootMounts) {
            if (rootMount.accepts(path)) {
                return rootMount;
            }
        }
        return null;
    }

}
