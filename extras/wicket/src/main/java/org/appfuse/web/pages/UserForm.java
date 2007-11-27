package org.appfuse.web.pages;

import org.apache.wicket.Page;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.collections.MicroMap;
import org.apache.wicket.util.string.interpolator.MapVariableInterpolator;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserForm extends BasePage {
    @SpringBean
    private UserManager userManager;
    private final Page backPage;

    /**
     * Constructor user to create a new user
     *
     * @param backPage page to navigate to after this page completes its work
     */
    public UserForm(Page backPage) {
        this(backPage, new User());
    }

    /**
     * Constructor used to edit an user
     *
     * @param backPage page to navigate to after this page completes its work
     * @param user     user to edit
     */
    public UserForm(final Page backPage, User user) {
        this.backPage = backPage;

        // Create and add the form
        EditForm form = new EditForm("user-form", user) {
            protected void onSave(User user) {
                onSaveUser(user);
            }

            protected void onCancel() {
                onCancelEditing();
            }

            protected void onDelete(User user) {
                onDeleteUser(user);
            }
        };
        add(form);
    }

    /**
     * Listener method for save action
     *
     * @param user user bean
     */
    protected void onSaveUser(User user) {
        userManager.saveUser(user);

        String message = MapVariableInterpolator.interpolate(getLocalizer().getString("user.saved", this),
                new MicroMap("name", user.getFullName()));
        getSession().info(message);
        backPage.get("feedback").setVisible(true);

        setRedirect(true);
        setResponsePage(backPage);
    }

    /**
     * Listener method for delete action
     *
     * @param user user bean
     */
    protected void onDeleteUser(User user) {
        userManager.removeUser(user.getId().toString());

        String message = MapVariableInterpolator.interpolate(getLocalizer().getString("user.deleted", this),
                new MicroMap("name", user.getFullName()));
        getSession().info(message);

        backPage.get("feedback").setVisible(true);
        setRedirect(true);
        setResponsePage(backPage);
    }

    /**
     * Lister method for cancel action
     */
    private void onCancelEditing() {
        setResponsePage(backPage);
    }

    /**
     * Subclass of Form used to edit an user
     *
     * @author ivaynberg
     */
    private static abstract class EditForm extends Form {
        /**
         * Convenience method that adds and prepares a form component
         *
         * @param fc    form component
         * @param label IModel containing the string used in ${label} variable of
         *              validation messages
         */
        private void add(FormComponent fc, IModel label) {
            // Add the component to the form
            super.add(fc);
            // Set its label model
            fc.setLabel(label);
            // Add feedback panel that will be used to display component errors
            add(new ComponentFeedbackPanel(fc.getId() + "-feedback", fc));
        }

        /**
         * Constructor
         *
         * @param id   component id
         * @param user User object that will be used as a form bean
         */
        public EditForm(String id, User user) {
            /*
             * We wrap the user bean with a CompoundPropertyModel, this allows
             * us to easily connect form components to the bean properties
             * (component id is used as the property expression)
             */
            super(id, new CompoundPropertyModel(user));
            add(new TextField("firstName"), new ResourceModel("user.firstName"));
            add(new RequiredTextField("lastName"), new ResourceModel("user.lastName"));
            add(new DateField("birthday"), new ResourceModel("user.birthday"));

            add(new Button("save", new Model("Save")) {
                public void onSubmit() {
                    onSave((User) getForm().getModelObject());
                }
            });

            Button delete = new Button("delete", new Model("Delete")) {
                public void onSubmit() {
                    onDelete((User) getForm().getModelObject());
                }
            };

            if (user.getId() == null) {
                delete.setVisible(false);
                delete.setEnabled(false);
            }
            add(delete);

            /*
             * Notice the setDefaultFormProcessing(false) call at the end. This
             * tells wicket that when this button is pressed it should not
             * perform any form processing (ie bind request values to the bean).
             */
            add(new Button("cancel", new Model("Cancel")) {
                public void onSubmit() {
                    onCancel();
                }
            }.setDefaultFormProcessing(false));

        }

        /**
         * Callback for cancel button
         */
        protected abstract void onCancel();

        /**
         * Callback for delete button
         *
         * @param user user bean
         */
        protected abstract void onDelete(User user);

        /**
         * Callback for save button
         *
         * @param user user bean
         */
        protected abstract void onSave(User user);
    }
}
