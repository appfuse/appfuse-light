[#ftl]
<!DOCTYPE html>

<html lang="en">
<head>
    <title>[#if title != '']${title}[#else]Welcome[/#if] |
           [#if rc??]${rc.getMessage("webapp.name")}[#else]AppFuse Light[/#if]</title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${base}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${base}/webjars/bootstrap/3.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/styles/app.css">
    <script type="text/javascript" src="${base}/webjars/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/webjars/bootstrap/3.3.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${base}/scripts/app.js"></script>
    ${head}
</head>
<body>
<a name="top"></a>
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${base}">AppFuse Light</a>
        </div>
        <div class="collapse navbar-collapse" id="navbar">
            <ul class="nav navbar-nav">
                <li><a href="${base}/" title="Home">Home</a></li>
                <li><a href="${base}/users" title="View Users">Users</a></li>
                <!-- Add new menu items here -->
            </ul>
        </div>
        [#if rc??]
        <script type="text/javascript">
            $('a[href="${rc.requestUri}"]').parent().addClass('active');
        </script>
        [/#if]
    </div>

    <div class="container">
        <div class="row">
            [#include "/messages.ftl"/]
            ${body}
        </div>
    </div>

    <div id="footer" class="container">
        <p>
            Created by <a href="http://appfuse.org">AppFuse</a>.
        </p>
    </div>
</body>
</html>
