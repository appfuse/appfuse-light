<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="index.title"/></title>

<div id="main">
    <h3>Welcome to AppFuse Light!</h3>
    <p>
        <a href="http://appfuse-light.dev.java.net">AppFuse Light</a> is a lightweight version of <a href="http://appfuse.org">AppFuse</a>.
        I was inspired to create it while writing <a href="http://springlive.com">Spring Live</a> and 
        looking at the <em>struts-blank</em> and <em>webapp-minimal</em> 
        applications that ship with Struts and Spring, respectively.
        These "starter" apps were not robust enough for me, and I wanted 
        something like AppFuse, only simpler.  Much of the documentation for developing
        with AppFuse Light can be found in the <a href="http://sourcebeat.com/titles/springlive/public/Rev_11/SpringLive_SampleChapter.pdf">
        Spring QuickStart Chapter</a> in Spring Live.  If you have issues downloading this 
        PDF, you might try saving it to your hard drive before opening it.
    </p>
    <p>
        The basic AppFuse Light application shows how to do simple
        <acronym title="Create, Retrieve, Update and Delete">CRUD</acronym> on a database table.  
        To see this feature, click on the button below. <a href="?" onclick="readMore(); return false">Click here</a> 
        to learn more about AppFuse Light.
    </p>
    <p>
        <button class="button" onclick="location.href='users.html'">View Demonstration</button>
    </p>
</div>
<div id="readmore" style="display:none">
    <h3>Introduction to AppFuse Light</h3>
    <p>
        AppFuse Light is designed to show webapp developers how to start
        a bare-bones webapp using a <a href="http://www.springframework.org">
        Spring</a>-managed middle-tier backend and <a href="http://www.hibernate.org">
        Hibernate</a> for persistence. By default, AppFuse Light uses Spring for
        its MVC framework, but you can change it to 
        <a href="http://struts.apache.org">Struts</a>, 
        <a href="http://opensymphony.com/webwork">WebWork</a>, 
        <a href="http://jakarta.apache.org/tapestry">Tapestry</a>
        or <a href="http://www.myfaces.org">JSF</a>.  Installers are in the "extras" directory.
    </p>
    <p>
        By default, AppFuse Light expects you to have PostgreSQL installed with
        an "appfuse-light" database created. For everything to work out-of-the-box,
        this database should be owned by a "postgres" user with the same
        password.  More information on database configuration can be found
        in this project's README.txt file.
    </p>
    <p> 
        Since no server configuration is required, this application should
        this application should work with any Servlet 2.4 servlet engine.
    </p>
    <p>
        <button class="button" onclick="readMore();">&laquo; Back</button>
    </p>
</div>
<content tag="underground">
<h3>Assumptions</h3>
<ul>
    <li>It's 2004, no one uses Netscape 4 anymore, or at least
        no one does by choice. All HTML will be XHTML compliant,
        without a space: i.e. &lt;br/&gt; not &lt;br /&gt;.</li>
    <li>JSP 2.0 is out, so it will be used to simplify syntax.</li>
    <li>Simplicity is more important than configurability.</li>
</ul>

<h3>Notes</h3>
<ul>
    <li>AppFuse Light ships with project files for both <a href="http://www.eclipse.org">Eclipse</a>
    and <a href="http://www.jetbrains.com/idea/">IDEA</a>. For information on setting up  
    AppFuse Light to run tests and debug Tomcat, see
    <a href="http://confluence.sourcebeat.com/display/SPL/FAQ">the FAQ</a>.</li>
    <li><a href="http://opensymphony.com/sitemesh">SiteMesh</a> is used for page decoration. It was
    <a href="http://raibledesigns.com/page/rd?anchor=sitemesh_passed_the_10_minute">
    so easy to use</a>, I couldn't resist!</li>
</ul>
</content>

<script type="text/javascript">
function readMore() {
    var main = document.getElementById("main");
    var more = document.getElementById("readmore");
    if (main.style.display == "") {
        main.style.display = "none";
        more.style.display = "";
    } else {
        more.style.display = "none";
        main.style.display = "";
    }
}
</script>
