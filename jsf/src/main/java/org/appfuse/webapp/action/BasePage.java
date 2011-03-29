package org.appfuse.webapp.action;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.util.*;

public class BasePage {
    protected final Log log = LogFactory.getLog(getClass());
    protected String sortColumn;
    protected boolean ascending;
    protected boolean nullsAreHigh;

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    // Convenience methods ====================================================
    public String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    public String getBundleName() {
        return getFacesContext().getApplication().getMessageBundle();
    }

    public ResourceBundle getBundle() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return ResourceBundle.getBundle(getBundleName(), getRequest().getLocale(), classLoader);
    }

    public String getText(String key) {
        String message;

        try {
            message = getBundle().getString(key);
        } catch (java.util.MissingResourceException mre) {
            log.warn("Missing key for '" + key + "'");
            return "???" + key + "???";
        }

        return message;
    }

    public String getText(String key, Object arg) {
        if (arg == null) {
            return getText(key);
        }

        MessageFormat form = new MessageFormat(getBundle().getString(key));

        if (arg instanceof String) {
            return form.format(new Object[]{arg});
        } else if (arg instanceof Object[]) {
            return form.format(arg);
        } else {
            log.error("arg '" + arg + "' not String or Object[]");

            return "";
        }
    }

    @SuppressWarnings("unchecked")
    protected void addMessage(String key, Object arg) {
        List<String> messages = (List) getSession().getAttribute("messages");

        if (messages == null) {
            messages = new ArrayList<String>();
        }

        messages.add(getText(key, arg));
        getSession().setAttribute("messages", messages);
    }

    protected void addMessage(String key) {
        addMessage(key, null);
    }

    @SuppressWarnings("unchecked")
    protected void addError(String key, Object arg) {
        List<String> errors = (List) getSession().getAttribute("errors");

        if (errors == null) {
            errors = new ArrayList<String>();
        }

        // if key contains a space, don't look it up, it's likely a raw message
        if (key.contains(" ") && arg == null) {
            errors.add(key);
        } else {
            errors.add(getText(key, arg));
        }

        getSession().setAttribute("errors", errors);
    }

    protected void addError(String key) {
        addError(key, null);
    }

    /**
     * Convenience method for unit tests.
     *
     * @return boolean indicator of an "errors" attribute in the session
     */
    public boolean hasErrors() {
        return (getSession().getAttribute("errors") != null);
    }

    /**
     * Servlet API Convenience method
     *
     * @return HttpServletRequest from the FacesContext
     */
    protected HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    /**
     * Servlet API Convenience method
     *
     * @return the current user's session
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }


    // The following methods are used by t:dataTable for sorting.
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    /**
     * Sort list according to which column has been clicked on.
     *
     * @param list the java.util.List to sort
     * @return ordered list
     */
    @SuppressWarnings("unchecked")
    protected List sort(List list) {
        Comparator comparator = new BeanComparator(sortColumn, new NullComparator(nullsAreHigh));
        if (!ascending) {
            comparator = new ReverseComparator(comparator);
        }
        Collections.sort(list, comparator);
        return list;
    }
}
