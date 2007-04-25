package org.appfuse.web;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * Comparator to allow comparing a List of beans and sorting decending on
 * a Date property.
 *
 * @author mraible
 */
public class BeanDateComparator extends BeanComparator {
    private static final long serialVersionUID = -5496825158557972554L;
 
    public BeanDateComparator(String property) {
        super(property);
    }

    @SuppressWarnings("unchecked")
    public int compare( Object o1, Object o2 ) {
        if ( getProperty() == null ) {
            // compare the actual objects
            return getComparator().compare(o1, o2);
        }

        try {
            Object value1 = PropertyUtils.getProperty( o1, getProperty() );
            Object value2 = PropertyUtils.getProperty( o2, getProperty() );

            if (value1 == null) {
                return -1;
            } else if (value2 == null) {
                return 1;
            }
            
            int result = getComparator().compare( value1, value2 );
            if (result == 1) return -1;
            else return 1;
        } catch (Exception e) {
            throw new ClassCastException( e.toString() );
        }
    }
}
