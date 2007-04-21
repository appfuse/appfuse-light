<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
    <link href="<ww:url value="/webwork/jscalendar/calendar-green.css"/>" rel="stylesheet" type="text/css" media="all"/>
</head>

<p>Please fill in user's information below:</p>
  
<ww:form name="userForm" action="saveUser" method="post" validate="true">
<ww:hidden name="user.id" value="%{user.id}"/>
    <ww:textfield label="%{getText('user.firstName')}" name="user.firstName"
        value="%{user.firstName}" id="user.firstName"/>
    <ww:textfield label="%{getText('user.lastName')}" name="user.lastName"
        value="%{user.lastName}" required="true"/>
    <ww:datepicker label="%{getText('user.birthday')}" name="user.birthday"
        size="11" format="%{getText('cal.date.format')}"/>
<tr>
    <td></td>
    <td>
        <input type="submit" class="button" name="save" value="Save" onclick="this.blur()"/>
    <c:if test="${not empty param.id}">
        <input type="submit" class="button" name="delete" value="Delete" onclick="form.onsubmit=null"/>
    </c:if>
        <input type="submit" class="button" name="cancel" value="Cancel" onclick="form.onsubmit=null"/>
    </td>
</ww:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["userForm"]);
</script>
