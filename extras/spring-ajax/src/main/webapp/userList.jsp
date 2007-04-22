<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userList.title"/></title>
    <script type="text/javascript" src="${ctx}/scripts/aa.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/lightbox.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/styles/lightbox.css"/>
</head>

<p>This table has been enhanced with <a href="http://ajaxanywhere.sf.net">AjaxAnywhere</a> to allow sorting and paging
    w/o refreshing the page.  You can also click on the first name to edit it inline.
    This is done with script.aculo.us and Spring MVC on the backend. Click on the last name to use the
    <a href="http://particletree.com/features/lightbox-gone-wild/">Lightbox</a> feature.
</p>

<button onclick="location.href='userform.html'"style="float: right; margin-top: -30px; width: 100px">Add User</button>
<aa:zone name="userTable">

<display:table name="users" class="table" requestURI="" id="userList" export="true" excludedParams="*" pagesize="5" sort="list">
    <display:setProperty name="export.pdf.filename" value="users.pdf"/>
    <display:column sortProperty="id" sortable="true" media="html" titleKey="user.id">
        <a href="${ctx}/userform.html?id=${userList.id}">${userList.id}</a>
    </display:column>
    <display:column property="id" media="csv excel xml pdf" titleKey="user.id"/>
    <display:column sortable="true" titleKey="user.firstName">
        <span id="first${userList_rowNum}" class="editable">${userList.firstName}</span>
    </display:column>
    <display:column sortProperty="lastName" sortable="true" titleKey="user.lastName">
        <a href="${ctx}/userform.html?id=${userList.id}" class="lbOn">${userList.lastName}</a>
    </display:column>
    <display:column titleKey="user.birthday" sortable="true" sortProperty="birthday">
        <fmt:formatDate value="${userList.birthday}" pattern="${datePattern}"/>
    </display:column>
</display:table>

</aa:zone>

<script type="text/javascript">
    ajaxAnywhere.getZonesToReload = function() { return "userTable" }
    ajaxAnywhere.onAfterResponseProcessing = function() {
        replaceLinks();
        initialize(); // re-initialize lightbox
    }
    function replaceLinks() {
        // replace all the links in <thead> with onclick's that call AjaxAnywhere
        var sortLinks = $('userList').getElementsByTagName('thead')[0]
                                     .getElementsByTagName('a');
        ajaxifyLinks(sortLinks);
        if (document.getElementsByClassName('pagelinks').length > 0) {
            var pagelinks = document.getElementsByClassName('pagelinks')[0]
                                    .getElementsByTagName('a');
            ajaxifyLinks(pagelinks);
        }
        //highlightTableRows("userList");
        var editable = document.getElementsByClassName('editable');
        for (i=0; i < editable.length; i++) {
            var userId = editable[i].parentNode.parentNode.getElementsByTagName("a")[0].innerHTML;
            new Ajax.InPlaceEditor(editable[i].id, 'users.html?id=' + userId);
        }
    }
    function ajaxifyLinks(links) {
        for (i=0; i < links.length; i++) {
            links[i].onclick = function() {
                ajaxAnywhere.getAJAX(this.href); 
                return false;
            }
        }
    }
    replaceLinks();
</script>

<div class="message">
<span id="cnt" style="font-weight: bold">0</span> seconds since last page refresh.
</div>
<script>
    var sec=0;
    function counter(){
        setTimeout("counter();",1000);
        document.getElementById("cnt").innerHTML = sec++;
    }
    counter();
</script>
