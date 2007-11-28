package org.appfuse.web;

import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
        model.addAttribute(userManager.getUsers());
        return "userList";
    }

    @RequestMapping("/user-ajax.*")
    public ModelAndView updateFirstName(@RequestParam("value") String value, @RequestParam("id") String id) {
        User user = userManager.getUser(id);
        user.setFirstName(value);
        userManager.saveUser(user);
        return new ModelAndView(new RedirectView("/ajaxResponse.jsp", true), "value", value);
    }
}