[#ftl]
<!DOCTYPE html>

<html lang="en">
<head>
    <title>[#if title != '']${title}[#else]Welcome[/#if] | ${rc.getMessage("webapp.name")}</title>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Expires" content="0"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <link rel="shortcut icon" href="${base}/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="${base}/webjars/bootstrap/2.2.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="${base}/styles/app.css">
    <script type="text/javascript" src="${base}/webjars/jquery/1.8.2/jquery.min.js"></script>
    <script type="text/javascript" src="${base}/webjars/bootstrap/2.2.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${base}/scripts/app.js"></script>
    ${head}
</head>
<body>
<a name="top"></a>
    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                [#-- For smartphones and smaller screens --]
                <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="brand" href="${base}">AppFuse Light</a>
                <div class="nav-collapse collapse">
                    <ul class="nav">
                        <li><a href="${base}/" title="Home">Home</a></li>
                        <li><a href="${base}/users" title="View Users">Users</a></li>
                        <!-- Add new menu items here -->
                    </ul>
                </div>
                <script type="text/javascript">
                    $('a[href="${rc.requestUri}"]').parent().addClass('active');
                </script>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span7">
                [#include "/messages.ftl"/]
                ${body}

                [#if page.getProperty("page.underground")?exists]
                ${page.getProperty("page.underground")}
                [/#if]
            </div>
            <div class="span2">
                <div id="branding">
                    <a href="http://appfuse.org" title="AppFuse - eliminating project startup time">
                        <img src="${base}/images/powered-by-appfuse.gif" width="203" height="75" alt="AppFuse"/></a>
                </div>

                <h3>Resources</h3>

                <p>The following is a list of resources that will make <a href="http://springframework.org">Spring</a> infinitely easier to use.</p>

                <ul class="glassList">
                    <li><a href="http://static.springsource.org/spring/docs/3.2.x/spring-framework-reference/html/">Spring 3.2 Docs</a></li>
                    <li><a href="http://static.springsource.org/spring/docs/3.2.x/javadoc-api/">Spring 3.2 API</a></li>
                    <li><a href="http://www.amazon.com/s/ref=nb_ss?url=search-alias%3Daps&field-keywords=spring+framework">Spring Books</a></li>
                    <li><a href="http://forum.springframework.org/">Spring Forums</a></li>
                </ul>

            </div>
        </div>
    </div>

    <div id="footer">
        <p>
            Created by <a href="http://appfuse.org">AppFuse</a>.
        </p>
    </div>
</body>
</html>
