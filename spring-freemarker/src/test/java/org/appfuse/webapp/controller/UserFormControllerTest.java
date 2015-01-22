package org.appfuse.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserFormControllerTest {
    final Log log = LogFactory.getLog(UserFormControllerTest.class);

    @InjectMocks
    private UserFormController controller;

    @Mock
    private UserManager userManager;

    private MockMvc mockMvc;
    private User user;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(controller).setViewResolvers(viewResolver).build();

        // Set messages to avoid NPE when controller calls getMessageSourceAccessor().getMessage()
        StaticApplicationContext ctx = new StaticApplicationContext();
        Map<String, String> properties = new HashMap<>();
        properties.put("basename", "messages");
        ctx.registerSingleton("messageSource", ResourceBundleMessageSource.class,
            new MutablePropertyValues(properties));
        ctx.refresh();
        controller.setMessages((MessageSource) ctx.getBean("messageSource"));

        // setup user values
        user = new User();
        user.setId(1L);
        user.setFirstName("Matt");
        user.setLastName("Raible");
    }

    @Test
    public void testEdit() throws Exception {
        log.debug("testing edit...");

        // set expected behavior on manager
        when(userManager.getUser("1")).thenReturn(user);

        mockMvc.perform(get("/userform").param("id", "1"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("user"))
            .andExpect(model().attribute("user", hasProperty("firstName", is("Matt"))))
            .andExpect(view().name("userform"));

        verify(userManager, times(1)).getUser("1");
    }

    @Test
    public void testSave() throws Exception {
        final User savedUser = user;
        savedUser.setLastName("Updated Last Name");

        ResultActions update = mockMvc.perform(post("/userform").param("user.lastName", savedUser.getLastName()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("users"));

        MvcResult result = update.andReturn();
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
        assertNotNull("success message is null", session.getAttribute(BaseFormController.MESSAGES_KEY));

        verify(userManager, times(1)).saveUser(savedUser);
    }

    @Test
    public void testRemove() throws Exception {
        ResultActions update = mockMvc.perform(post("/userform")
            .param("id", user.getId().toString())
            .param("delete", ""))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("users"));

        MvcResult result = update.andReturn();
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
        assertNotNull("success message is null", session.getAttribute(BaseFormController.MESSAGES_KEY));

        verify(userManager, times(1)).removeUser("1");
    }
}
