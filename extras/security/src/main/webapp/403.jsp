<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>

<page:applyDecorator name="default">

<title>403 ~ Access Denied</title>
<content tag="heading">Access Denied</content>

<p>
    Your current role does not allow you to view this page.  Please contact your system
    administrator if you believe you should have access.  In the meantime, how about a pretty
    picture to cheer you up?
</p>
<p style="text-align: center; margin-top: 20px">
    <a href="http://community.webshots.com/photo/56793801/56801692jkyHaR" title="Hawaii, click to Zoom In">
    <img style="border: 0" src="${pageContext.request.contextPath}/images/403.jpg" alt="Hawaii" /></a>
</p>
</page:applyDecorator>