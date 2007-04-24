package org.appfuse.web;

import wicket.protocol.http.WebApplication;
import wicket.spring.injection.annot.SpringComponentInjector;

public class Application extends WebApplication {
    public void init() {
        // notice in 2.0+ versions this is replaced by new SpringWicketModule(this);
        addComponentInstantiationListener(new SpringComponentInjector(this));
    }

    public Class getHomePage() {
        return Index.class;
    }
}