package org.appfuse.webapp.action;

import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.ActionBean;

public class BaseActionBean implements ActionBean {
    private ActionBeanContext context;

    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = context; }
}
