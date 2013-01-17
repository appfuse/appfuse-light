package org.appfuse.webapp.controller;

import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    UserManager userManager;

    @RequestMapping("/users*")
    public String execute(ModelMap model) {
        model.addAttribute("userList", userManager.getUsers());
        return "userList";
    }
}
