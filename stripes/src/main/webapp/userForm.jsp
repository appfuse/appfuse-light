<%@ include file="/taglibs.jsp" %>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<p>Please fill in user's information below:</p>

<stripes:form action="/userform.html" id="userForm">
    <stripes:errors globalErrorsOnly="true"/>
    <stripes:hidden name="user.id"/>
    <stripes:hidden name="user.version"/>
    <table class="detail">
        <tr>
            <th><label for="username"><fmt:message key="user.username"/>:</label></th>
            <td>
                <stripes:text name="user.username" id="username" class="text medium"/>
                <stripes:errors field="user.username"/>
            </td>
        </tr>
        <tr>
            <th><label for="password"><fmt:message key="user.password"/>:</label></th>
            <td>
                <stripes:password name="user.password" id="password" class="text medium" repopulate="true"/>
                <stripes:errors field="user.password"/>
            </td>
        </tr>
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
            <th><label for="email"><fmt:message key="user.email"/>:</label></th>
            <td>
                <stripes:text name="user.email" id="email" class="text medium"/>
                <stripes:errors field="user.email"/>
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
</script>
