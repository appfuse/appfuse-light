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
    <link rel="stylesheet" href="${base}/webjars/bootstrap/3.3.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/styles/app.css">
    <script type="text/javascript" src="${base}/webjars/jquery/1.11.1/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/webjars/bootstrap/3.3.0/js/bootstrap.min.js"></script>
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
            <div class="col-sm-7">
                [#include "/messages.ftl"/]
                ${body}

                [#if page.getProperty("page.underground")?exists]
                ${page.getProperty("page.underground")}
                [/#if]
            </div>
            <div class="col-sm-2">
                <div id="branding">
                    <a href="http://appfuse.org" title="AppFuse - eliminating project startup time">
                        <img src="${base}/images/powered-by-appfuse.gif" width="203" height="75" alt="AppFuse"/></a>
                </div>
                <h3>Resources</h3>

                <p>The following is a list of resources that will make <a href="http://springframework.org">Spring</a> infinitely easier to use.</p>

                <ul class="glassList">
                    <li><a href="http://docs.spring.io/spring/docs/4.0.x/spring-framework-reference/html/">Spring 4.0 Docs</a></li>
                    <li><a href="http://docs.spring.io/spring/docs/4.0.x/javadoc-api/">Spring 4.0 API</a></li>
                    <li><a href="http://www.amazon.com/s/ref=nb_ss?url=search-alias%3Daps&amp;field-keywords=spring+framework">Spring Books</a></li>
                    <li><a href="http://forum.spring.io/">Spring Forums</a></li>
                </ul>

            </div>
        </div>
    </div>

    <div id="footer" class="container">
        <p>
            Created by <a href="http://appfuse.org">AppFuse</a>.
        </p>
    </div>
</body>
</html>
