package org.appfuse.web.pages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.Component;
import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.IModel;
import wicket.model.Model;

public abstract class BasePage extends WebPage {
    protected Log log = LogFactory.getLog(getClass());
    
    /**
     * IModel implementation used to display the page title. Default to an empty
     * string
     */
    private IModel pageTitleModel = new Model("");

    /**
     * Constructor
     */
    public BasePage() {
        /*
         * This is a simple model that pulls its model object from the
         * pageTitleModel. This allows the label components that display the
         * title to always have the freshest set title.
         * 
         * If we did not do this we would have to update the label objects
         * whenever setPageTitle() is called.
         * 
         * This can also be achieved using a PropertyModel: final IModel
         * titleModel=new PropertyModel(this, "pageTitleModel"). Both are
         * functionaly equivalent, but this model with standup better to
         * automatic IDE refactorings.
         */
        final IModel titleModel = new AbstractReadOnlyModel() {
            public Object getObject(Component component) {
                return pageTitleModel.getObject(component);
            }
        };

        // A label to display the page title
        add(new Label("page-title", titleModel));
    }

    /**
     * Sets the model that contains page title
     * 
     * @param pageTitleModel
     */
    public void setPageTitle(IModel pageTitleModel) {
        this.pageTitleModel = pageTitleModel;
    }
}
