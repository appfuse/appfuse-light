package org.appfuse.webapp.pages;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.appfuse.model.User;
import org.appfuse.service.UserManager;

public class SortableUserDataProvider extends SortableDataProvider<User, String> {
    private UserManager userManager;

    public SortableUserDataProvider(UserManager userManager) {
        this.userManager = userManager;
    }

    public SortableUserDataProvider() {
        // default sort
        setSort("firstName", SortOrder.ASCENDING);
    }

    /**
     * @todo implement paging in the database, this is just stubbed out for now
     */
    @SuppressWarnings("unchecked")
    public Iterator iterator(long first, long count) {
        List users = userManager.getUsers();
        if (first > 0) {
            users = users.subList((int) first, (int) (first + count));
        }

        SortParam sp = getSort();

        if (sp != null) {
            Object sortColumn = sp.getProperty();
            Comparator comparator;

            comparator = new BeanComparator(sortColumn.toString());

            if (!sp.isAscending()) {
                comparator = new ReverseComparator(comparator);
            }

            Collections.sort(users, comparator);
        }

        return users.iterator();
    }

    public long size() {
        return userManager.getUsers().size();
    }

    @Override
    public IModel<User> model(User user) {
        return new UserModel(user, userManager);
    }
}
