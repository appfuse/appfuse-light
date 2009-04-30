package org.appfuse.web.pages;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class SortableUserDataProvider extends SortableDataProvider {
    private UserManager userManager;
    
    public SortableUserDataProvider(UserManager userManager) {
        this.userManager = userManager;
    }
    
    public SortableUserDataProvider() {
        // default sort
        setSort("firstName", true);
    }
    
    /**
     * @todo implement paging in the database, this is just stubbed out for now
     */
    @SuppressWarnings("unchecked")
    public Iterator iterator(int first, int count) {
        List users = userManager.getUsers();
        if (first > 0) {
            users = users.subList(first, first + count);
        }
        
        SortParam sp = getSort();
        
        if (sp != null) {
            String sortColumn = sp.getProperty();
            Comparator comparator;
    
            comparator = new BeanComparator(sortColumn);
    
            if (!sp.isAscending()) {
                comparator = new ReverseComparator(comparator);
            }
            
            Collections.sort(users, comparator);
        }

        return users.iterator();
    }

    public IModel model(Object object) {
        return new UserModel((User) object, userManager);
    }

    public int size() {
        return userManager.getUsers().size();
    }
}
