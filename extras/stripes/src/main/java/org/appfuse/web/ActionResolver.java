package org.appfuse.web;

import net.sourceforge.stripes.controller.NameBasedActionResolver;

public class ActionResolver extends NameBasedActionResolver {

    /**
     * Used to convert URLs to lowercase. Calculator -> calculator
     * @param name The name of the action
     * @return the normal name, except all lowercase
     */
    protected String getUrlBinding(String name) {
        return super.getUrlBinding(name).toLowerCase();
    }

    /**
     * Set default suffix to .html instead of .action
     * @return ".html"
     */
    protected String getBindingSuffix() {
        return ".html";
    }
}
