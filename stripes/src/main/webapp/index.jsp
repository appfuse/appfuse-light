<%@ include file="/taglibs.jsp"%>

<div id="intro">
    <h2>Welcome to AppFuse Light!</h2>
    <p>
        <a href="http://appfuse-light.java.net">AppFuse Light</a> is a lightweight version of <a href="http://appfuse.org">AppFuse</a>.
        I was inspired to create it while writing <a href="http://springlive.com">Spring Live</a> and
        looking at the <em>struts-blank</em> and <em>webapp-minimal</em>
        applications that ship with Struts and Spring, respectively.
        These "starter" apps were not robust enough for me, and I wanted
        something like AppFuse, only simpler.
    </p>
    <p>
        The basic AppFuse Light application shows how to do simple
        <abbr title="Create, Retrieve, Update and Delete">CRUD</abbr> on a database table.
        To see this feature, click on the button below. <a href="?" onclick="readMore(); return false">Click here</a>
        to learn more about AppFuse Light.
    </p>
    <p>
        <button class="btn btn-primary" onclick="location.href='users'">View Demonstration</button>
    </p>
</div>
<div id="readmore" style="display:none">
    <h3>Introduction to AppFuse Light</h3>
    <p>
        AppFuse Light is designed to show Java Web Developers how to start
        a bare-bones webapp using a <a href="http://www.springframework.org">
        Spring</a>-managed middle-tier backend and <a href="http://www.hibernate.org">
        Hibernate</a> for persistence. In addition to Hibernate, you can also use JPA.
    </p>
    <p>
        You can use many different web frameworks for your UI, including:
    </p>
    <ul>
        <li><a href="http://myfaces.apache.org">JSF/MyFaces</a></li>
        <li><a href="http://springframework.org">Spring MVC</a></li>
        <li><a href="http://stripesframework.org">Stripes</a></li>
        <li><a href="http://struts.apache.org/2.x/">Struts 2</a></li>
        <li><a href="http://tapestry.apache.org">Tapestry</a></li>
        <li><a href="http://wicket.apache.org/">Wicket</a></li>
    </ul>
    <p>
        In addition, there's a couple of extras for Spring MVC, including FreeMarker and
        Spring MVC versions. All versions are available using
        <a href="http://static.appfuse.org/light/archetypes.html">Maven Archetypes</a>.
    </p>
    <p>
        By default, AppFuse Light uses an in-memory H2 database. It will create it on-the-fly
        the first time tests are run or the application is started. More information on database
        configuration can be found in this project's README.txt file.
    </p>
    <p>
        Since no server configuration is required, this application should
        this application should work with any Servlet 3.0 servlet engine.
    </p>
    <p>
        <button class="btn" onclick="readMore();">&laquo; Back</button>
    </p>
</div>
<content tag="underground">
<h3>Assumptions</h3>
<ul>
    <li>It's 2013, you should be thinking about HTML5, Mobile and REST.</li>
    <li>JRebel should be used to allow quick reloading of classes.</li>
    <li>Conventions are more important than configurability.</li>
</ul>
</content>

<script type="text/javascript">
function readMore() {
    var main = document.getElementById("intro");
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
