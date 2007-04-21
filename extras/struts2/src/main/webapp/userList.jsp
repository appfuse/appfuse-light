<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="userList.title"/></title>

<s:set name="users" value="users" scope="request"/>
<button onclick="location.href='editUser.html'">Add User</button>

<display:table name="users" class="list" requestURI="" id="userList" export="true" pagesize="10">
    <display:column property="id" sortable="true" href="editUser.html" media="html"
        paramId="id" paramProperty="id" titleKey="user.id"/>
    <display:column property="id" media="csv excel xml pdf" titleKey="user.id"/>
    <display:column property="firstName" sortable="true" titleKey="user.firstName"/>
    <display:column property="lastName" sortable="true" titleKey="user.lastName"/>
    <display:column sortable="true" titleKey="user.birthday" sortProperty="birthday">
        <fmt:formatDate value="${userList.birthday}" pattern="${datePattern}"/>
    </display:column>
</display:table>

<script type="text/javascript">highlightTableRows("userList");</script>
