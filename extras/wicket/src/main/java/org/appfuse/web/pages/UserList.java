package org.appfuse.web.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

        SortableDataProvider dp = new SortableUserDataProvider(userManager);

        final DataView dataView = new DataView("users", dp) {
            protected void populateItem(final Item item) {
                User user = (User) item.getModelObject();

                Link link = new Link("edit-link", item.getModel()) {
                    public void onClick() {
                        onEditUser((User) getModelObject());
                    }
                };
                link.add(new Label("user.id", String.valueOf(user.getId())));

                item.add(link);
                item.add(new Label("user.firstName", user.getFirstName()));
                item.add(new Label("user.lastName", user.getLastName()));

                DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
                if (user.getBirthday() != null) {
                    item.add(new Label("user.birthday", dateFormatter.format(user.getBirthday())));
                } else {
                    item.add(new Label("user.birthday", ""));
                }
                item.add(new AttributeModifier("class", true, new LoadableDetachableModel() {
                    protected Object load() {
                        return (item.getIndex() % 2 == 1) ? "even" : "odd";
                    }
                }));
            }
        };

        dataView.setItemsPerPage(10);
        add(new OrderByBorder("orderById", "id", dp));
        add(new OrderByBorder("orderByFirstName", "firstName", dp));
        add(new OrderByBorder("orderByLastName", "lastName", dp));
        add(new OrderByBorder("orderByBirthday", "birthday", dp));
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
