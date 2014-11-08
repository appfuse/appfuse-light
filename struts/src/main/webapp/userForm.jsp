<%@ include file="/taglibs.jsp" %>

<head>
    <title><fmt:message key="userForm.title"/></title>
</head>

<h2><fmt:message key="userForm.title"/></h2>
<p>Please fill in user's information below:</p>

<s:form name="userForm" action="saveUser" theme="bootstrap" method="post"
        cssClass="well" validate="true">
    <s:hidden name="user.id" value="%{user.id}"/>
    <s:hidden name="user.version" value="%{user.version}"/>
    <s:textfield key="user.username" required="true"/>
    <s:password key="user.password" showPassword="true" required="true"/>
    <s:textfield key="user.firstName"/>
    <s:textfield key="user.lastName"/>
    <s:textfield key="user.email" required="true"/>
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
</s:form>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['userForm']).focus();
        $('a[href="${ctx}/users"]').parent().addClass('active');
    });
</script>
