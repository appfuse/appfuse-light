<%@ include file="/taglibs.jsp" %>

<head>
    <title><fmt:message key="userForm.title"/></title>
    <%-- Calendar Setup - put in decorator if needed in multiple pages --%>
    <link href="${ctx}/styles/calendar.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/lang/calendar-en.js"></script>
</head>

<p>Please fill in user's information below:</p>

<s:form name="userForm" action="saveUser" method="post" validate="true">
    <s:hidden name="user.id" value="%{user.id}"/>
    <s:hidden name="user.version" value="%{user.version}"/>
    <s:textfield key="user.username"/>
    <s:password key="user.password" showPassword="true"/>
    <s:textfield key="user.firstName"/>
    <s:textfield key="user.lastName"/>
    <s:textfield key="user.email"/>
    <tr>
        <td></td>
        <td>
            <input type="submit" class="button" name="save" value="Save" onclick="this.blur()"/>
            <c:if test="${not empty param.id}">
                <input type="submit" class="button" name="delete" value="Delete" onclick="form.onsubmit=null"/>
            </c:if>
            <input type="submit" class="button" name="cancel" value="Cancel" onclick="form.onsubmit=null"/>
        </td>
    </tr>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["userForm"]);
</script>
