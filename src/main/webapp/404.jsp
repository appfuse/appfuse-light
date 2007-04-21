<%-- SiteMesh has a bug where error pages aren't decorated - hence the full HTML --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title>404 ~ Page Not Found</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <link href="${ctx}/styles/global.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/images/favicon.ico" rel="SHORTCUT ICON"/>
    <!-- HTML & Design contributed by Boer Attila (http://www.calcium.ro) -->
    <!-- Found at http://www.csszengarden.com/?cssfile=/083/083.css&page=2 -->
</head>
<body>
<a name="top"></a>
<div id="container">
    <div id="intro">
        <div id="pageHeader">
            <h1><span>Page Not Found</span></h1>
            <div id="logo" onclick="location.href='<c:url value="/"/>'"
                onkeypress="location.href='<c:url value="/"/>'"></div>
            <h2><span>404</span></h2>
        </div>
    
        <div id="quickSummary">
            <p>
                <a href="http://appfuse-light.dev.java.net">AppFuse Light</a> is a lightweight version of 
                <a href="http://raibledesigns.com/appfuse">AppFuse</a> designed 
                to accelerate starting a webapp with the 
                <a href="http://www.springframework.org">Spring Framework</a>.
            </p>
            <p class="credit">
                <a href="http://www.csszengarden.com/?cssfile=/083/083.css&amp;page=2">
                Design and CSS</a> donated by <a href="http://www.calcium.ro">
                Bo&eacute;r Attila</a>.
            </p>
        </div>
    
        <div id="content">
            <p>
            The page your requested was not found.  You might try returning to the 
            <a href="<c:url value="/"/>">welcome page</a>. While you&#39;re here, how 
            about a pretty picture to cheer you up? 
            </p>

            <p style="text-align: center; margin-top: 20px">
                <a href="http://community.webshots.com/photo/87848122/87848260vtOXvy"
                    title="Emerald Lake - Western Canada, click to Zoom In">
                <img style="border: 0"
                    src="<c:url value="/images/404.jpg"/>"
                    alt="Emerald Lake - Western Canada" /></a>
            </p>
        </div>
    </div>

    <div id="supportingText">
        <div id="underground"></div>
        <div id="footer">
            <a href="http://validator.w3.org/check/referer" title="Check the validity of this site&#8217;s XHTML">xhtml</a> &middot;
            <a href="http://jigsaw.w3.org/css-validator/check/referer" title="Check the validity of this site&#8217;s CSS">css</a> &middot;
            <a href="http://www.apache.org/licenses/LICENSE-2.0" title="View the license for AppFuse Light, courtesy of Apache Software Foundation.">license</a> &middot;
            <a href="http://bobby.watchfire.com/bobby/bobbyServlet?URL=<c:out value="${pageContext.request.requestURL}"/>&amp;output=Submit&amp;gl=sec508&amp;test=" title="Check the accessibility of this site according to U.S. Section 508">508</a> &middot;
            <a href="http://bobby.watchfire.com/bobby/bobbyServlet?URL=<c:out value="${pageContext.request.requestURL}"/>&amp;output=Submit&amp;gl=wcag1-aaa&amp;test=" title="Check the accessibility of this site according to WAI Content Accessibility Guidelines 1">aaa</a>
        </div>

    </div>
	
    <div id="linkList">
        <div id="linkList2">
            <div id="lresources">
            </div>
        </div>
    </div>

</div>

</body>
</html>


