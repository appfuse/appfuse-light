package org.appfuse.web.pages;

import wicket.Component;
import wicket.model.AbstractReadOnlyModel;

/**
 * A simple model that represents a resource by its key
 * 
 * @author ivaynberg
 */
public class ResourceModel extends AbstractReadOnlyModel {
    private String key;

    /**
     * Constructor
     * 
     * @param key
     *            key of the resource this model represents
     */
    public ResourceModel(String key) {
        this.key = key;
    }

    /**
     * @see wicket.model.AbstractReadOnlyModel#getObject(wicket.Component)
     */
    public Object getObject(Component component) {
        return component.getLocalizer().getString(key, component);
    }

}
