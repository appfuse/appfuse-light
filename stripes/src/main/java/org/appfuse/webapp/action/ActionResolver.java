package org.appfuse.webapp.action;

import net.sourceforge.stripes.controller.NameBasedActionResolver;

public class ActionResolver extends NameBasedActionResolver {

    /**
     * Used to convert URLs to lowercase. Calculator -> calculator
     * @param name The name of the action
     * @return the normal name, except all lowercase
     */
    protected String getUrlBinding(String name) {
        if (name != null) {
            return super.getUrlBinding(name).toLowerCase();
        }
        return name;
    }
}
