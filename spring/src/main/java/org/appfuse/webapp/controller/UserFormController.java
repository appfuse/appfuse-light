package org.appfuse.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.appfuse.model.User;
import org.appfuse.service.UserExistsException;
import org.appfuse.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/userform*")
public class UserFormController extends BaseFormController {
    private final Log log = LogFactory.getLog(UserFormController.class);

    @Autowired
    UserManager userManager;

    @Autowired(required = false)
    Validator validator;


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(User user, BindingResult result, HttpServletRequest request) throws Exception {

        if (request.getParameter("cancel") != null) {
            return "redirect:users";
        }

        if (validator != null && request.getParameter("delete") == null) { // validator is null during testing
            validator.validate(user, result);

            if (result.hasErrors()) {
                return "userform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        if (request.getParameter("delete") != null) {
            userManager.removeUser(user.getId().toString());
            saveMessage(request, getText("user.deleted", user.getFullName(), request.getLocale()));
        } else {
            try {
                userManager.saveUser(user);
            } catch (UserExistsException uex) {
                result.addError(new ObjectError("user", uex.getMessage()));
                return "userform";
            }
            saveMessage(request, getText("user.saved", user.getFullName(), request.getLocale()));
        }

        return "redirect:users";
    }

    @ModelAttribute
    @RequestMapping(method = RequestMethod.GET)
    protected User getUser(HttpServletRequest request) {
        String userId = request.getParameter("id");
        if ((userId != null) && !userId.equals("")) {
            return userManager.getUser(userId);
        } else {
            return new User();
        }
    }
}
