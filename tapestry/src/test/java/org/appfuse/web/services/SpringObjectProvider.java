package org.appfuse.web.services;

import org.apache.tapestry5.ioc.AnnotationProvider;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ObjectProvider;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author Serge Eby
 * @version $Id: SpringObjectProvider.java 5 2008-08-30 09:59:21Z serge.eby $
 */
public class SpringObjectProvider implements ObjectProvider {
    private TypeCoercer typeCoercer;
    private ApplicationContext context;
    private static final Logger log = LoggerFactory.getLogger(SpringObjectProvider.class.getName());

    public SpringObjectProvider(@InjectService("TypeCoercer") TypeCoercer typeCoercer) {
        this.typeCoercer = typeCoercer;
        JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource) getSpringContext().getBean("dataSource"));
        jdbcTemplate.execute("delete from app_user");
    }

    /**
     * @see org.apache.tapestry5.ioc.ObjectProvider#provide(java.lang.Class, org.apache.tapestry5.ioc.AnnotationProvider, org.apache.tapestry5.ioc.ObjectLocator)
     */
    public <T> T provide(Class<T> objectType,
                         AnnotationProvider annotationProvider, ObjectLocator locator) {

        // hack...
        if (objectType.getName().startsWith("org.apache.tapestry5")) {
            return null;
        }

        Inject annotation = annotationProvider.getAnnotation(Inject.class);

        if (annotation == null) {
            return null;
        }
        Object obj = null;

        try {
            String[] beanNames = getSpringContext().getBeanNamesForType(objectType);

            if (beanNames.length > 0) {
                // return first result
                //log.info("returning bean name: " + beanNames[0]);
                obj = getSpringContext().getBean(beanNames[0]);
            } else {
                //log.info("Couldn't find a bean for type " + objectType.getName());
            }
        } catch (Exception e) {
            log.warn("Problem occurred when finding a bean for type " + objectType.getName(), e);
        }

        T coerced = typeCoercer.coerce(obj, objectType);

        return coerced;

    }

    private ApplicationContext getSpringContext() {
        if (context == null) {
            //log.info("Returning Spring context");
            context = new ClassPathXmlApplicationContext(new String[]{
                    "classpath:/applicationContext-resources.xml",
                    "classpath:/applicationContext-dao.xml",
                    "classpath:/applicationContext-service.xml",
                    "classpath*:/applicationContext.xml",
                    "/WEB-INF/applicationContext*.xml",
            });
        }
        return context;
    }

}