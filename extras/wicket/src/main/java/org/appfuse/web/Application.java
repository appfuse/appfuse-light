package org.appfuse.web;

import wicket.protocol.http.WebApplication;

public class Application extends WebApplication {
    public Class getHomePage() {
        return Index.class;
    }
}