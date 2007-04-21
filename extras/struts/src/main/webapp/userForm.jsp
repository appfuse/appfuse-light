<%@ include file="/taglibs.jsp"%>

<title><fmt:message key="userForm.title"/></title>

<p>Please fill in user's information below:</p>

<html:form action="/user" focus="user.firstName" onsubmit="return validateUserForm(this)">
<input type="hidden" name="method" value="save"/>
<html:hidden property="user.id"/>
<table class="detail">
<tr>
    <th><label for="user.firstName"><fmt:message key="user.firstName"/>:</label></th>
    <td><html:text property="user.firstName" styleId="user.firstName"/></td>
</tr>
<tr>
    <th><label for="user.lastName" class="required">* <fmt:message key="user.lastName"/>:</label></th>
    <td>
        <html:text property="user.lastName" styleId="user.lastName"/>
        <span class="fieldError"><html:errors property="user.lastName"/></span>
    </td>
</tr>
    <th><label for="user.birthday"><fmt:message key="user.birthday"/>:</label></th>
    <td>
        <c:set var="datePattern"><fmt:message key="date.format"/></c:set>
        <input type="text" size="11" name="user.birthday" id="user.birthday"
            value="<fmt:formatDate value="${userForm.map.user.birthday}" 
            pattern="${datePattern}"/>"/> [${datePattern}]
    </td>
<tr>
    <td></td>
    <td>
        <html:submit styleClass="button" property="save">Save</html:submit>
      <c:if test="${not empty param.id}">
        <html:submit styleClass="button" property="delete"
            onclick="this.form.method.value='delete'">Delete</html:submit>
      </c:if>
      	<html:cancel styleClass="button">Cancel</html:cancel>
    </td>
</tr>
</table>
</html:form>

<html:javascript formName="userForm" staticJavascript="false" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
