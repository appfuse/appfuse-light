package org.appfuse.web;

import wicket.markup.html.WebPage;
import wicket.markup.html.basic.Label;

public class Index extends WebPage {
    public Index() {
        add(new Label("message", "Hello World!"));
    }
}
