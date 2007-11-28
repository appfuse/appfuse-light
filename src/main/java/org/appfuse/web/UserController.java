package org.appfuse.web;

import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserManager userManager;

    // need for testing
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    @RequestMapping("/users.*")
    public String execute(ModelMap model) {
        model.addAttribute("userList", userManager.getUsers());
        return "userList";
    }
}
