<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
    <%-- Calendar Setup - put in decorator if needed in multiple pages --%>
    <link  href="${ctx}/styles/calendar.css"  type="text/css"  rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/lang/calendar-en.js"></script>
</head>

<p>Please fill in user's information below:</p>

<form:form commandName="user" method="post" action="userform.html" onsubmit="return validateUser(this)" id="userForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id"/>
<table class="detail">
<tr>
    <th><label for="firstName"><fmt:message key="user.firstName"/>:</label></th>
    <td>
        <form:input path="firstName" id="firstName"/>
        <form:errors path="firstName" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="lastName" class="required">* <fmt:message key="user.lastName"/>:</label></th>
    <td>
        <form:input path="lastName" id="lastName"/>
        <form:errors path="lastName" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="birthday"><fmt:message key="user.birthday"/>:</label></th>
    <td>
        <form:input path="birthday" id="birthday" size="11"/>
        <button id="birthdayCal" type="button" class="button"> ... </button> [<fmt:message key="date.format"/>]
        <form:errors path="birthday" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <input type="submit" class="button" name="save" value="Save"/>
      <c:if test="${not empty param.id}">
        <input type="submit" class="button" name="delete" value="Delete" onclick="bCancel=true"/>
      </c:if>
      	<input type="submit" class="button" name="cancel" value="Cancel" onclick="bCancel=true"/>
    </td>
</tr>
</table>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('userForm'));
    Calendar.setup(
    {
        inputField  : "birthday",      // id of the input field
        ifFormat    : "%m/%d/%Y",      // the date format
        button      : "birthdayCal"    // id of the button
    }
    );
</script>

<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
