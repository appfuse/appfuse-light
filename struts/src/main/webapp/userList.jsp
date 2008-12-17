<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>

<s:set name="users" value="users" scope="request"/>
<button onclick="location.href='editUser.html'"style=" float: right; margin-top: -30px; width: 100px">Add User</button>

<display:table name="users" class="table" requestURI="" id="userList" export="true" pagesize="10">
    <display:column property="id" sortable="true" href="editUser.html" media="html"
        paramId="id" paramProperty="id" titleKey="user.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="user.id"/>
    <display:column property="firstName" sortable="true" titleKey="user.firstName" escapeXml="true"/>
    <display:column property="lastName" sortable="true" titleKey="user.lastName" escapeXml="true"/>
    <display:column property="email" sortable="true" titleKey="user.email" escapeXml="true"/>
</display:table>

<script type="text/javascript">highlightTableRows("userList");</script>
