package org.appfuse.webapp.pages;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.appfuse.webapp.Application;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserFormTest {

    private WicketTester tester;

    private UserManager userManager;

    @Before
    public void onSetUp() {
        tester = new WicketTester();
        userManager = mock(UserManager.class);
        ApplicationContextMock applicationContextMock = new ApplicationContextMock();
        applicationContextMock.putBean("userManager", userManager);
        tester.getApplication().getComponentInstantiationListeners().add(
            new SpringComponentInjector(tester.getApplication(), applicationContextMock));
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
        tester.assertErrorMessages(new String[]{
            "'Username' is required.",
            "'Password' is required.",
            "'E-Mail' is required."});
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
        tester.assertInfoMessages(new String[]{
            "User " + user.getFirstName() + " " + user.getLastName() + " has been saved successfully."
        });
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
