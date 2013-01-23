package org.appfuse.webapp.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SupportsInformalParameters;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.internal.TapestryInternalUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.services.Request;
import org.appfuse.webapp.internal.MenuContext;

import java.util.List;

/**
 * The menu item component represents each list item in the menu.
 * The parameters are used to define the link inside the item when applicable
 *
 * @author Serge Eby
 */

@SupportsInformalParameters
public class MenuItem {

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
    private String name;

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.MESSAGE, allowNull = false)
    private String title;

    @Property
    @Parameter(required = false, defaultPrefix = BindingConstants.LITERAL, allowNull = false)
    private String page;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = true)
    private String description;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = true)
    private String style;

    @Property
    @Parameter(value = "false", defaultPrefix = BindingConstants.PROP, allowNull = false)
    private boolean disabled;


    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL, allowNull = true)
    private String roles;

    @Property
    @Parameter(name = "class", defaultPrefix = BindingConstants.LITERAL, allowNull = true)
    private String itemCssClass;

    @Inject
    private ComponentResources resources;

    @Inject
    private Request request;

    @Environmental
    private MenuContext menuContext;


    public String getUrl() {

        if (page == null) {
            return "#";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(request.getContextPath());
        if (page != null && !page.startsWith("/")) {
            sb.append("/");
        }
        sb.append(page);

        return sb.toString();
    }

    void beginRender(MarkupWriter writer) {

        List<String> itemClass = CollectionFactory.newList();

        Element e = writer.element("li");
        if (itemCssClass != null) {
            itemClass.add(itemCssClass);
        }

        if (menuContext.isActive(name)) {
            itemClass.add("active");
        }

        if (!itemClass.isEmpty()) {
            e.addClassName(TapestryInternalUtils.toClassAttributeValue(itemClass));
        }

        Element href = writer.element("a",
                "id", name,
                "href", getUrl(),
                "title", title
        );

        if (style != null) {
            href.attribute("style", style);
        }

        resources.renderInformalParameters(writer);
        writer.write(title);
        writer.end(); //a

    }

    void afterRender(MarkupWriter writer) {
        writer.end();    // li
    }

}
