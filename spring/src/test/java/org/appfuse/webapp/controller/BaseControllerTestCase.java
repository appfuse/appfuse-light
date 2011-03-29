package org.appfuse.webapp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration(
    locations = {"classpath:/applicationContext-resources.xml",
        "classpath:/applicationContext-dao.xml",
        "classpath:/applicationContext-service.xml",
        "/WEB-INF/applicationContext*.xml",
        "/WEB-INF/dispatcher-servlet.xml"})
public abstract class BaseControllerTestCase extends AbstractTransactionalJUnit4SpringContextTests {
  protected transient final Log log = LogFactory.getLog(getClass());

  /**
   * Convenience methods to make tests simpler
   *
   * @param url the URL to post to
   * @return a MockHttpServletRequest with a POST to the specified URL
   */
  public MockHttpServletRequest newPost(String url) {
    return new MockHttpServletRequest("POST", url);
  }

  public MockHttpServletRequest newGet(String url) {
    return new MockHttpServletRequest("GET", url);
  }
}
