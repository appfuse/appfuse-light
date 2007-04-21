package org.appfuse.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.tapestry.html.BasePage;
import org.appfuse.service.UserManager;

public abstract class UserList extends BasePage {
    public abstract UserManager getUserManager();
    public abstract String getMessage();
    public abstract void setMessage(String message);
    
    private DateFormat dateFormatter = 
        new SimpleDateFormat("MM/dd/yyyy");
    
    public String getFormattedDate(java.util.Date date) {
        if (date == null) {
            return "";
        }
        
        return dateFormatter.format(date);
    }
}
