<%@ include file="/taglibs.jsp" %>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<p>Please fill in user's information below:</p>

<stripes:form action="/userform.action" id="userForm" class="well form-horizontal">
    <stripes:errors globalErrorsOnly="true"/>
    <stripes:hidden name="user.id"/>
    <stripes:hidden name="user.version"/>

    <div class="control-group${empty(actionBean.context.validationErrors['user.username']) ? '' : ' error'}">
        <label for="username" class="control-label">* <fmt:message key="user.username"/>:</label>
        <div class="controls">
            <stripes:text name="user.username" id="username"/>
            <stripes:errors field="user.username"/>
        </div>
    </div>
    <div class="control-group${empty(actionBean.context.validationErrors['user.password']) ? '' : ' error'}">
        <label for="password" class="control-label">* <fmt:message key="user.password"/>:</label>
        <div class="controls">
            <stripes:password name="user.password" id="password" class="text medium" repopulate="true"/>
            <stripes:errors field="user.password"/>
        </div>
    </div>
    <div class="control-group${empty(actionBean.context.validationErrors['user.firstName']) ? '' : ' error'}">
        <label for="firstName" class="control-label"><fmt:message key="user.firstName"/>:</label>
        <div class="controls">
            <stripes:text name="user.firstName" id="firstName"/>
            <stripes:errors field="user.firstName"/>
        </div>
    </div>
    <div class="control-group${empty(actionBean.context.validationErrors['user.lastName']) ? '' : ' error'}">
        <label for="lastName" class="control-label"><fmt:message key="user.lastName"/>:</label>
        <div class="controls">
            <stripes:text name="user.lastName" id="lastName"/>
            <stripes:errors field="user.lastName"/>
        </div>
    </div>
    <div class="control-group${empty(actionBean.context.validationErrors['user.email']) ? '' : ' error'}">
        <label for="email" class="control-label"><fmt:message key="user.email"/>:</label>
        <div class="controls">
            <stripes:text name="user.email" id="email"/>
            <stripes:errors field="user.email"/>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save" id="save">
            <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
        </button>

        <c:if test="${not empty param.id}">
            <button type="submit" class="btn" name="delete" id="delete">
                <i class="icon-trash"></i> <fmt:message key="button.delete"/>
            </button>
        </c:if>

        <a href="${ctx}/users" class="btn" id="cancel">
            <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </a>
    </div>
</stripes:form>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['userForm']).focus();
    });
</script>
