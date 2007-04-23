package org.appfuse.web;

import net.sourceforge.jwebunit.WebTestCase;

import java.util.ResourceBundle;

public class UserWebTest extends WebTestCase {
	private ResourceBundle messages;

    public UserWebTest(String name) {
        super(name);
        getTestContext().setBaseUrl("http://localhost:8080");
        getTestContext().setResourceBundleName("messages");
        messages = ResourceBundle.getBundle("messages");
        //getTestContext().setLocale(Locale.GERMAN);
        //getTestContext().getWebClient().setHeaderField("Accept-Language", "de");
    }

    public void testWelcomePage() {
        beginAt("/");
        assertTitleKeyMatches("index.title");
    }

    public void testCalculator() {
        beginAt("/calculator.html");
        assertTitleEquals("My First Stripe | AppFuse Light");
    }

    protected void assertTitleKeyMatches(String title) {
        assertTitleEquals(messages.getString(title) + " | " + messages.getString("webapp.name"));
    }
}
