<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<div class="col-sm-3">
    <h2><fmt:message key="userForm.title"/></h2>
    <p>Please fill in user's information.</p>
</div>
<div class="col-sm-6">
    <spring:bind path="user.*">
        <c:if test="${not empty status.errorMessages}">
            <div class="alert alert-error fade in">
                <a href="#" data-dismiss="alert" class="close">&times;</a>
                <c:forEach var="error" items="${status.errorMessages}">
                    <c:out value="${error}" escapeXml="false"/><br/>
                </c:forEach>
            </div>
        </c:if>
    </spring:bind>

    <form:form commandName="user" method="post" action="userform" autocomplete="off"
               onsubmit="return validateUser(this)" id="userForm" cssClass="well">
        <form:hidden path="id"/>
        <form:hidden path="version"/>

        <spring:bind path="user.username">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <label for="username" class="control-label">* <fmt:message key="user.username"/>:</label>
            <form:input path="username" id="username" required="true" cssClass="form-control"/>
            <form:errors path="username" cssClass="help-block"/>
        </div>
        <spring:bind path="user.password">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <label for="password" class="control-label">* <fmt:message key="user.password"/>:</label>
            <form:password path="password" id="password" showPassword="true" required="true" cssClass="form-control"/>
            <form:errors path="password" cssClass="help-block"/>
        </div>
        <spring:bind path="user.firstName">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <label for="firstName" class="control-label"><fmt:message key="user.firstName"/>:</label>
            <form:input path="firstName" id="firstName" cssClass="form-control"/>
            <form:errors path="firstName" cssClass="help-block"/>
        </div>
        <spring:bind path="user.lastName">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <label for="lastName" class="control-label"><fmt:message key="user.lastName"/>:</label>
            <form:input path="lastName" id="lastName" cssClass="form-control"/>
            <form:errors path="lastName" cssClass="help-block"/>
        </div>
        <spring:bind path="user.email">
        <div class="form-group${(not empty status.errorMessage) ? ' has-error' : ''}">
        </spring:bind>
            <label for="email" class="control-label">* <fmt:message key="user.email"/>:</label>
            <form:input path="email" id="email" required="true" cssClass="form-control"/>
            <form:errors path="email" cssClass="help-block"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" id="save">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>

            <c:if test="${not empty param.id}">
              <security:authorize ifAllGranted="ROLE_ADMIN">
                <button type="submit" class="btn btn-danger" name="delete" id="delete">
                    <i class="icon-trash"></i> <fmt:message key="button.delete"/>
                </button>
              </security:authorize>
            </c:if>

            <a href="${ctx}/users" class="btn btn-default" id="cancel">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </a>
        </div>
    </form:form>

    <script type="text/javascript">
        $(document).ready(function() {
            $("input[type='text']:visible:enabled:first", document.forms['userForm']).focus();
        });
    </script>

    <v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>
    <script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</div>
