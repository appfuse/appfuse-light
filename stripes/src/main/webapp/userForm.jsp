<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<div class="col-sm-3">
    <h2><fmt:message key="userForm.title"/></h2>
    <p>Please fill in user's information.</p>
</div>
<div class="col-sm-6">
    <stripes:errors globalErrorsOnly="true"/>

    <stripes:form action="/userform.action" id="userForm" class="well">
        <stripes:hidden name="user.id"/>
        <stripes:hidden name="user.version"/>

        <div class="form-group${empty(actionBean.context.validationErrors['user.username']) ? '' : ' has-error'}">
            <label for="username" class="control-label">* <fmt:message key="user.username"/>:</label>
            <stripes:text name="user.username" id="username" class="form-control"/>
            <stripes:errors field="user.username"/>
        </div>
        <div class="form-group${empty(actionBean.context.validationErrors['user.password']) ? '' : ' has-error'}">
            <label for="password" class="control-label">* <fmt:message key="user.password"/>:</label>
            <stripes:password name="user.password" id="password" class="form-control" repopulate="false"/>
            <stripes:errors field="user.password"/>
        </div>
        <div class="form-group${empty(actionBean.context.validationErrors['user.firstName']) ? '' : ' has-error'}">
            <label for="firstName" class="control-label"><fmt:message key="user.firstName"/>:</label>
            <stripes:text name="user.firstName" id="firstName" class="form-control"/>
            <stripes:errors field="user.firstName"/>
        </div>
        <div class="form-group${empty(actionBean.context.validationErrors['user.lastName']) ? '' : ' has-error'}">
            <label for="lastName" class="control-label"><fmt:message key="user.lastName"/>:</label>
            <stripes:text name="user.lastName" id="lastName" class="form-control"/>
            <stripes:errors field="user.lastName"/>
        </div>
        <div class="form-group${empty(actionBean.context.validationErrors['user.email']) ? '' : ' has-error'}">
            <label for="email" class="control-label"><fmt:message key="user.email"/>:</label>
            <stripes:text name="user.email" id="email" class="form-control"/>
            <stripes:errors field="user.email"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" id="save">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>

            <c:if test="${not empty param.id}">
                <button type="submit" class="btn btn-danger" name="delete" id="delete">
                    <i class="icon-trash"></i> <fmt:message key="button.delete"/>
                </button>
            </c:if>

            <a href="${ctx}/users" class="btn btn-default" id="cancel">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </a>
        </div>
    </stripes:form>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['userForm']).focus();
    });
</script>
