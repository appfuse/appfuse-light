package org.appfuse.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hivemind.Messages;
import org.apache.hivemind.impl.MessageFormatter;
import org.apache.tapestry.IPage;
import org.apache.tapestry.test.Creator;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public abstract class BasePageTestCase extends AbstractDependencyInjectionSpringContextTests {
    protected final Log log = LogFactory.getLog(getClass());
    protected final static String EXTENSION = ".html";
    
    protected String[] getConfigLocations() {
        return new String[] {"/WEB-INF/applicationContext*.xml"};
    }

    protected IPage getPage(Class clazz) {
        return getPage(clazz, null);
    }
    
    protected IPage getPage(Class clazz, Map properties) {
        Creator creator = new Creator();
        if (properties == null) {
            properties = new HashMap();
        }
        
        Messages messages = new MessageFormatter(log, "messages");
        properties.put("messages", messages);
        
        return (IPage) creator.newInstance(clazz, properties);
    }
}
