package org.appfuse.webapp.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

/**
 * Page to manage and display users.
 * 
 * @author mraible
 */
public class UserList extends BasePage {
    private static final long serialVersionUID = -5202104862675278153L;
    @SpringBean
    private UserManager userManager;

    public UserList() {
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
        feedbackPanel.setVisible(false); // other pages will set this to visible
        feedbackPanel.setEscapeModelStrings(false);
        
        // Form and button for routing user to add a new user
        Form form = new Form("form");
        Button button = new Button("add-user") {
            public void onSubmit() {
                onEditUser(new User());
            }
        };
        button.setDefaultFormProcessing(false);
        form.add(button);
        add(form);

        SortableUserDataProvider dp = new SortableUserDataProvider(userManager);

        final DataView<User> dataView = new DataView<User>("users", dp) {
            protected void populateItem(final Item<User> item) {
                User user = item.getModelObject();

                Link<User> link = new Link<User>("edit-link", item.getModel()) {
                    public void onClick() {
                        onEditUser(getModelObject());
                    }
                };
                link.add(new Label("user.firstName", user.getFirstName()));

                item.add(link);
                item.add(new Label("user.lastName", user.getLastName()));
                item.add(new Label("user.email", user.getEmail()));

                item.add(new AttributeModifier("class", true, new LoadableDetachableModel() {
                    protected Object load() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        };

        dataView.setItemsPerPage(10);
        add(new OrderByBorder("orderByFirstName", "firstName", dp));
        add(new OrderByBorder("orderByLastName", "lastName", dp));
        add(new OrderByBorder("orderByEmail", "email", dp));
        add(dataView);
        add(new PagingNavigator("navigator", dataView));
    }
    
    /**
     * Listener for the edit action
     * 
     * @param user
     *            user to be edited
     */
    protected void onEditUser(User user) {
        setResponsePage(new UserForm(this, user));
    }
}
