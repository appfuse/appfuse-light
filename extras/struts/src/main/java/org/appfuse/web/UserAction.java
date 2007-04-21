package org.appfuse.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class UserAction extends DispatchAction {
    private final Log log = LogFactory.getLog(UserAction.class);
    private UserManager mgr = null;

    public void setUserManager(UserManager userManager) {
        this.mgr = userManager;
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'delete' method...");
        }

        DynaActionForm userForm = (DynaActionForm) form;
        User user = (User) userForm.get("user");

        mgr.removeUser(request.getParameter("user.id"));

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("user.deleted", user.getFullName()));

        saveMessages(request.getSession(), messages);

        return mapping.findForward("users");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'edit' method...");
        }

        DynaActionForm userForm = (DynaActionForm) form;
        String userId = request.getParameter("id");

        // null userId indicates an add
        if (userId != null) {
            User user = mgr.getUser(userId);

            if (user == null) {
                ActionMessages errors = new ActionMessages();
                errors.add(ActionMessages.GLOBAL_MESSAGE,
                        new ActionMessage("user.missing"));
                saveErrors(request, errors);

                return mapping.findForward("list");
            }

            userForm.set("user", user);
        }

        MessageResources resources = getResources(request);
        // register a date editor to handle dates
        DateLocaleConverter dateConverter = 
            new DateLocaleConverter(null, request.getLocale(), 
                    resources.getMessage("date.format"));
        dateConverter.setLenient(true);
        ConvertUtils.register(dateConverter, Date.class);
        
        return mapping.findForward("edit");
    }

    public ActionForward list(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'list' method...");
        }

        request.setAttribute("users", mgr.getUsers());

        return mapping.findForward("list");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("entering 'save' method...");
        }

        if (isCancelled(request)) {
            return list(mapping, form, request, response);
        }
        
        // run validation rules on this form
        ActionMessages errors = form.validate(mapping, request);

        if (!errors.isEmpty()) {
            saveErrors(request, errors);

            return mapping.findForward("edit");
        }

        DynaActionForm userForm = (DynaActionForm) form;
        User user = (User) userForm.get("user");

        mgr.saveUser(user);

        ActionMessages messages = new ActionMessages();
        messages.add(ActionMessages.GLOBAL_MESSAGE,
                new ActionMessage("user.saved", user.getFullName()));
        saveMessages(request.getSession(), messages);

        return mapping.findForward("users");
    }

    public ActionForward unspecified(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws Exception {
        return list(mapping, form, request, response);
    }
    
    static {
        ConvertUtils.register(new LongConverter(null), Long.class);
    }
}
