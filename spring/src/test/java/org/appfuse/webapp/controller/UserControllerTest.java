package org.appfuse.webapp.controller;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserManager userManager;

    private MockMvc mockMvc;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        // set expected behavior on manager
        User user1 = new User();
        user1.setFirstName("ControllerTest");
        final List<User> users = new ArrayList<User>();
        users.add(user1);
        when(userManager.getUsers()).thenReturn(users);
    }

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(post("/users"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("userList"))
            .andExpect(view().name("userList"));
    }
}
