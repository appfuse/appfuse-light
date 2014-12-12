package org.appfuse.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    locations = {"classpath:/applicationContext-resources.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-service.xml",
        "/WEB-INF/applicationContext*.xml",
        "/WEB-INF/dispatcher-servlet.xml"})
public abstract class BaseControllerTestCase {
    protected transient final Log log = LogFactory.getLog(getClass());
}
