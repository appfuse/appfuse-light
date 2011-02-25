<%@ include file="/taglibs.jsp" %>

<!DOCTYPE html>

<html lang="en">
<head>
    <title><decorator:title default="Welcome"/> | <fmt:message key="webapp.name"/></title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="${ctx}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/deliciouslyblue/theme.css" title="default" />
    <link rel="alternate stylesheet" type="text/css" href="${ctx}/styles/deliciouslygreen/theme.css" title="green" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.0.3/prototype.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/scriptaculous/1.8.1/scriptaculous.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/stylesheetswitcher.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/rsh.compressed.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/global.js"></script>
    <decorator:head/>
</head>
<body>
<div id="ajaxLoading" style="display: none; position: absolute; top: 0; right: 0; background: red; padding: 5px 10px; color: white">Loading...</div>
<a name="top"></a>
<div id="page">

    <div id="header" class="clearfix">

        <h1 style="cursor: pointer" onclick="location.href='${ctx}/'">AppFuse Light</h1>

        <div id="branding">
            <a href="http://appfuse.org" title="AppFuse - eliminating project startup time">
                <img src="${ctx}/images/powered-by-appfuse.gif" width="203" height="75" alt="AppFuse"/></a>
        </div>

        <p><fmt:message key="webapp.tagline"/></p>
    </div>

    <div id="content">

        <div id="main">
            <h1><decorator:title/></h1>
            <div id="body">
                <%@ include file="/messages.jsp"%>
                <decorator:body/>

                <div id="underground"><decorator:getProperty property="page.underground"/></div>
            </div>
        </div>

        <div id="sub">
            <h3>Resources</h3>

            <p>The following is a list of resources that will make <a href="http://springframework.org">Spring</a> infinitely easier to use.</p>

            <ul class="glassList">
                <li><a href="http://static.springsource.org/spring/docs/3.0.x/spring-framework-reference/html/">Spring 3.0 Docs</a></li>
                <li><a href="http://static.springsource.org/spring/docs/3.0.x/javadoc-api/">Spring 3.0 API</a></li>
                <li><a href="http://www.amazon.com/s/ref=nb_ss?url=search-alias%3Daps&field-keywords=spring+framework">Spring Books</a></li>
                <li><a href="http://forum.springframework.org/">Spring Forums</a></li>
                <li><a href="http://springmodules.dev.java.net">Spring Modules</a></li>
            </ul>

            <img src="${ctx}/images/image.gif" alt="Click to Change Theme" width="150" height="112" class="right" style="margin: 10px 0 20px 0"
                 onclick="StyleSheetSwitcher.setActive((StyleSheetSwitcher.getActive() == 'default') ? 'green' : 'default')"/>
        </div>

        <div id="nav">
            <div class="wrapper">
                <h2 class="accessibility">Navigation</h2>
                <ul class="clearfix">
                    <li><a href="${ctx}/" title="Home"><span>Home</span></a></li>
                    <li><a href="${ctx}/users" title="View Users"><span>Users</span></a></li>
                </ul>
            </div>
        </div><!-- end nav -->

    </div><!-- end content -->

    <div id="footer">
        <p>
            <a href="http://www.oswd.org/design/preview/id/2634">Deliciously Blue</a> from <a href="http://www.oswd.org/">OSWD</a> |
            Design by <a href="http://www.oswd.org/user/profile/id/8377">super j man</a>
        </p>
    </div>
</div>
</body>
</html>
