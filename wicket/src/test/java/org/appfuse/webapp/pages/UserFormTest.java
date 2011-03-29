package org.appfuse.webapp.pages;

import org.apache.wicket.util.tester.FormTester;
import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.junit.Before;
import org.junit.Test;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.injection.annot.test.AnnotApplicationContextMock;
import org.apache.wicket.util.tester.ITestPageSource;
import org.apache.wicket.util.tester.WicketTester;

import java.util.Locale;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UserFormTest {

    private WicketTester tester;
    private UserManager userManager;

    @Before
    public void onSetUp() {
        tester = new WicketTester();
        tester.setupRequestAndResponse();
        tester.getWicketSession().setLocale(new Locale("en"));  //to ensure error messages will be in English

        AnnotApplicationContextMock context = new AnnotApplicationContextMock();
        userManager = mock(UserManager.class);
        context.putBean("userManager", userManager);
        tester.getApplication().addComponentInstantiationListener(
                new SpringComponentInjector(tester.getApplication(), context, true));

        tester.startPage(new ITestPageSource() {
            public Page getTestPage() {
                return new UserForm(new UserList());
            }
        });
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
        tester.assertErrorMessages(new String[] {
                "Field 'Username' is required.",
                "Field 'Password' is required.", 
                "Field 'E-Mail' is required." });
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
        tester.assertInfoMessages(new String[] {
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
