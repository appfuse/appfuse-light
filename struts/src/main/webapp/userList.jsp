<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>
<h2><fmt:message key="userList.title"/></h2>

<s:set name="users" value="users" scope="request"/>
<button class="btn btn-primary" onclick="location.href='editUser'"style="float: right; margin-top: -30px">
    <i class="icon-plus icon-white"></i> Add User</button>

<display:table name="users" class="table table-condensed table-striped table-hover" requestURI="" id="userList" export="true" pagesize="10">
    <display:column property="id" sortable="true" href="editUser" media="html"
        paramId="id" paramProperty="id" titleKey="user.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="user.id"/>
    <display:column property="firstName" sortable="true" titleKey="user.firstName" escapeXml="true"/>
    <display:column property="lastName" sortable="true" titleKey="user.lastName" escapeXml="true"/>
    <display:column property="email" sortable="true" titleKey="user.email" escapeXml="true"/>
</display:table>