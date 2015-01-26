package org.appfuse.webapp.pages;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.wicket.markup.html.WebPage;

public abstract class AbstractWebPage extends WebPage {
    transient protected Log log = LogFactory.getLog(getClass());
}
