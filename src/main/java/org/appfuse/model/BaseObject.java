package org.appfuse.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Base class for Model objects.  This is basically for the toString method.
 *
 * @author Matt Raible
 */
public class BaseObject implements Serializable {
    private static final long serialVersionUID = 3256446889040622647L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
