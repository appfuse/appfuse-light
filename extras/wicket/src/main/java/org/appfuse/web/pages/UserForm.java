package org.appfuse.web.pages;

import java.util.Date;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;

import wicket.Page;
import wicket.extensions.markup.html.datepicker.DatePicker;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.FormComponent;
import wicket.markup.html.form.RequiredTextField;
import wicket.markup.html.form.TextField;
import wicket.markup.html.panel.ComponentFeedbackPanel;
import wicket.model.CompoundPropertyModel;
import wicket.model.IModel;
import wicket.model.Model;
import wicket.spring.injection.annot.SpringBean;
import wicket.util.collections.MicroMap;
import wicket.util.string.interpolator.MapVariableInterpolator;

public class UserForm extends BasePage {
    private static final IModel TITLE = new ResourceModel("userForm.title");
    @SpringBean
    private UserManager userManager;
    private final Page backPage;

    /**
     * Constructor user to create a new user
     * 
     * @param backPage
     *            page to navigate to after this page completes its work
     */
    public UserForm(Page backPage) {
        this(backPage, new User());
    }

    /**
     * Constructor used to edit an user
     * 
     * @param backPage
     *            page to navigate to after this page completes its work
     * @param user
     *            user to edit
     */
    public UserForm(final Page backPage, User user) {
        this.backPage = backPage;
        setPageTitle(TITLE);

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
     * 
     */
    private static abstract class EditForm extends Form {
        /**
         * Convenience method that adds and prepares a form component
         * 
         * @param fc
         *            form component
         * @param label
         *            IModel containing the string used in ${label} variable of
         *            validation messages
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
         * @param id
         *            component id
         * @param user
         *            User object that will be used as a form bean
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
            
            TextField birthdayField = new TextField("birthday", Date.class);
            add(birthdayField, new ResourceModel("user.birthday"));
            add(new DatePicker("birthdayPicker", birthdayField));

            add(new Button("save", new Model("Save")) {
                protected void onSubmit() {
                    onSave((User) getForm().getModelObject());
                }
            });

            Button delete = new Button("delete", new Model("Delete")) {
                protected void onSubmit() {
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
                protected void onSubmit() {
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
         * @param user user bean
         */
        protected abstract void onDelete(User user);

        /**
         * Callback for save button
         * @param user user bean
         */
        protected abstract void onSave(User user);
    }
}
