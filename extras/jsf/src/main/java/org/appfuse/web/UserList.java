package org.appfuse.web;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;

import org.appfuse.service.UserManager;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class UserList implements Serializable {
    private UserManager userManager;
    private String sortColumn = "id";
    private boolean ascending = true;

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

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    public List getUsers() {
        List users = userManager.getUsers();

        Comparator comparator = null;

        if (sortColumn.equalsIgnoreCase("birthday")) {
            comparator = new BeanDateComparator(sortColumn);
        } else {
            comparator = new BeanComparator(sortColumn);
        }

        if (!ascending) {
            comparator = new ReverseComparator(comparator);
        }
        
        Collections.sort(users, comparator);

        return users;
    }
}
