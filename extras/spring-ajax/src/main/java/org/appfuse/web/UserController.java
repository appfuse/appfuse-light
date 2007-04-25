package org.appfuse.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

public class UserController implements Controller {
    private final Log log = LogFactory.getLog(UserController.class);
    private UserManager mgr = null;

    public void setUserManager(UserManager userManager) {
        this.mgr = userManager;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
                                      HttpServletResponse response)
    throws Exception {
        log.debug("entering 'handleRequest' method...");

        String value = request.getParameter("value");
        if (value != null) {
            String id = request.getParameter("id");
            User user = mgr.getUser(id);
            user.setFirstName(value);
            mgr.saveUser(user);
            return new ModelAndView(new RedirectView("/ajaxResponse.jsp", true), "value", value);
        }
        return new ModelAndView("userList", "users", mgr.getUsers());
    }
}
