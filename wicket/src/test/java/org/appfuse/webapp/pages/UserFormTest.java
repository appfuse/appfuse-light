package org.appfuse.webapp.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.util.tester.FormTester;
import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.junit.Test;
import org.springframework.web.context.support.StaticWebApplicationContext;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserFormTest extends BasePageTest {

    private UserManager userManager;

    @Override
    protected void initSpringBeans(StaticWebApplicationContext context) {
        super.initSpringBeans(context);
        userManager = mock(UserManager.class);
        context.getBeanFactory().registerSingleton("userManager", userManager);
        UserForm userForm = new UserForm();
        tester.startPage(userForm);
    }

    @Test
    public void shouldDisplayErrorMessagesOnAddEmptyUser() {
        //given
        tester.assertRenderedPage(UserForm.class);
        tester.assertNoErrorMessage();
        tester.assertComponent("user-form", Form.class);

        //when
        tester.submitForm("user-form");

        //then
        tester.assertErrorMessages("'Username' is required.",
                "'Password' is required.",
                "'E-Mail' is required.");
    }

    @Test
    public void shouldSaveUserAndRedirectToUserListOnSubmitUserWithRequiredData() throws UserExistsException {
        //given
        tester.assertRenderedPage(UserForm.class);
        tester.assertNoErrorMessage();
        FormTester formTester = tester.newFormTester("user-form");
        assertEquals("", formTester.getTextComponentValue("username"));
        User user = createTestUser();

        //when
        fillFormWithValuesFromUser(formTester, user);
        formTester.submit("save");

        //then
        verify(userManager).saveUser(user);
        tester.assertRenderedPage(UserList.class);
        tester.assertNoErrorMessage();
        tester.assertInfoMessages("User " + user.getFirstName() + " " + user.getLastName() +
                        " has been saved successfully.");
    }

    private User createTestUser() {
        User user = new User("bravetester");
        user.setPassword("secret");
        user.setEmail("brave@tester.com");
        user.setFirstName("Brave");
        user.setLastName("Tester");
        return user;
    }

    private void fillFormWithValuesFromUser(FormTester formTester, User user) {
        formTester.setValue("username", user.getUsername());
        formTester.setValue("password", user.getPassword());
        formTester.setValue("email", user.getEmail());
        formTester.setValue("firstName", user.getFirstName());
        formTester.setValue("lastName", user.getLastName());
    }
}
