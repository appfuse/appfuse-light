package org.appfuse.web.pages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import wicket.AttributeModifier;
import wicket.Component;
import wicket.extensions.markup.html.repeater.data.DataView;
import wicket.extensions.markup.html.repeater.data.sort.OrderByBorder;
import wicket.extensions.markup.html.repeater.refreshing.Item;
import wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.link.Link;
import wicket.markup.html.navigation.paging.PagingNavigator;
import wicket.markup.html.panel.FeedbackPanel;
import wicket.model.AbstractReadOnlyModel;
import wicket.model.IModel;
import wicket.model.ResourceModel;
import wicket.spring.injection.annot.SpringBean;

/**
 * Page to manage and display users.
 * 
 * @author mraible
 */
public class UserList extends BasePage {
    private static final long serialVersionUID = -5202104862675278153L;
    private static final IModel TITLE = new ResourceModel("userList.title");
    @SpringBean
    private UserManager userManager;

    public UserList() {
        setPageTitle(TITLE);
        
        FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
        add(feedbackPanel);
        feedbackPanel.setVisible(false); // other pages will set this to visible
        feedbackPanel.setEscapeMessages(false);
        
        // Form and button for routing user to add a new user
        Form form = new Form("form");
        Button button = new Button("add-user") {
            protected void onSubmit() {
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
                item.add(new AttributeModifier("class", true, new AbstractReadOnlyModel() {
                    public Object getObject(Component component) {
                        Item item = (Item) component;
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
