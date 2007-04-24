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

<stripes:form action="/userform.html" id="userForm">
<stripes:errors globalErrorsOnly="true"/>
<stripes:hidden name="user.id"/>
<table class="detail">
<tr>
    <th><label for="firstName"><fmt:message key="user.firstName"/>:</label></th>
    <td>
        <stripes:text name="user.firstName" id="firstName" class="text medium"/>
        <stripes:errors field="user.firstName"/>
    </td>
</tr>
<tr>
    <th><label for="lastName" class="required">* <fmt:message key="user.lastName"/>:</label></th>
    <td>
        <stripes:text name="user.lastName" id="lastName" class="text medium"/>
        <stripes:errors field="user.lastName"/>
    </td>
</tr>
<tr>
    <th><label for="birthday"><fmt:message key="user.birthday"/>:</label></th>
    <td>
        <stripes:text name="user.birthday" id="birthday" size="11" class="text small"/>
        <button id="birthdayCal" type="button" class="button"> ... </button> [<fmt:message key="date.format"/>]
        <stripes:errors field="user.birthday"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <stripes:submit name="save" value="Save" class="button"/>
      <c:if test="${not empty param.id}">
        <stripes:submit name="delete" value="Delete" class="button"/>
      </c:if>
        <stripes:submit name="cancel" value="Cancel" class="button"/>
    </td>
</tr>
</table>
</stripes:form>

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
