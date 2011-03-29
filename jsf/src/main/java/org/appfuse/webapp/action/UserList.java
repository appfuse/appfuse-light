package org.appfuse.webapp.action;

import java.io.Serializable;
import static java.util.Collections.sort;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.appfuse.service.UserManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component("userList")
@Scope("session")
public class UserList implements Serializable {
    private UserManager userManager;
    private String sortColumn = "id";
    private boolean ascending = true;

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

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
    public List getUsers() {
        List users = userManager.getUsers();

        Comparator comparator;

        if (sortColumn.equalsIgnoreCase("birthday")) {
            comparator = new BeanDateComparator(sortColumn);
        } else {
            comparator = new BeanComparator(sortColumn);
        }

        if (!ascending) {
            comparator = new ReverseComparator(comparator);
        }
        
        sort(users, comparator);

        return users;
    }
}
