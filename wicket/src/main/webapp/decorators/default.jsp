<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp" %>
<html lang="en">
<head>
    <title><decorator:title default="Welcome"/> | <fmt:message key="webapp.name"/></title>
    <meta http-equiv="Cache-Control" content="no-store"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${ctx}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${ctx}/webjars/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/styles/app.css">
    <script type="text/javascript" src="${ctx}/webjars/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/webjars/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/app.js"></script>
    <decorator:head/>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
<a name="top"></a>

    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value='/'/>">AppFuse Light</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="nav navbar-nav">
                <li><a href="${ctx}/" title="Home">Home</a></li>
                <li><a href="${ctx}/users" title="View Users">Users</a></li>
                <!-- Add new menu items here -->
            </ul>
        </div>
        <script type="text/javascript">
            $('a[href="${pageContext.request.requestURI}"]').parent().addClass('active');
        </script>
    </div>

    <div class="container">
        <div class="row">
            <decorator:body/>
        </div>
    </div>

    <div id="footer" class="container">
        <p>
            Created by <a href="http://appfuse.org">AppFuse</a>.
        </p>
    </div>
</body>
</html>
