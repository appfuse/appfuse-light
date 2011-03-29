package org.appfuse.webapp.rootmount;

import org.apache.wicket.protocol.http.request.InvalidUrlException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Utilities to work with raw paths as given to {@link RootMountedUrlCodingStrategy#accepts(String)}.
 * <p>
 * Assumes that the servlet container is configured to work with UTF-8 URLs.
 *
 * @author Erik van Oosten
 */
public class RawPathUtil {

    /**
     * Decode and split the path into its path parts.
     *
     * @param rawPath the raw request path (can be empty or null)
     * @return the path split on "/", or null when rawPath is empty or null
     * @throws org.apache.wicket.protocol.http.request.InvalidUrlException when the URL could not be decoded
     */
    public static String[] splitToPathParts(String rawPath) {
        if (rawPath == null || rawPath.length() == 0) {
            return null;
        }
        try {
            return URLDecoder.decode(rawPath, "UTF-8").split("/");
        } catch (UnsupportedEncodingException e) {
            throw new InvalidUrlException("Could not decode URL");
        }
    }

    /**
     * Decode and give the first path part.
     *
     * @param rawPath the raw request path (can be empty or null)
     * @return the first path part or null when there is none
     * @throws org.apache.wicket.protocol.http.request.InvalidUrlException when the URL could not be decoded
     */
    public static String firstPathPath(String rawPath) {
        String[] pathParts = splitToPathParts(rawPath);
        if (pathParts == null || pathParts.length == 0) {
            return null;
        }
        String firstPathPart = pathParts[0];
        return firstPathPart == null || firstPathPart.length() == 0 ? null : firstPathPart;
    }

}
