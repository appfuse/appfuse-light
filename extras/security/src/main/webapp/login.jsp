<%@ include file="/taglibs.jsp"%>

<title>AppFuse Light ~ Login</title>

<p>
    Please enter your username and password to login.
    User tomcat/tomcat has a <strong>User</strong> role,
    while mraible/tomcat has an <strong>Admin</strong> role.
</p>

<form action="<c:url value="/j_security_check"/>" id="loginForm" method="post">
<p>
    <c:if test="${param.error == 'true'}">
        <div class="error">${sessionScope.ACEGI_SECURITY_LAST_EXCEPTION.message}</div>
    </c:if>

    <label for="username">Username</label><br/>
    <input id="username" type="text" name="j_username"/><br/>

    <label for="password">Password</label><br/>
    <input id="password" type="password" name="j_password"/><br/>

    <input type="checkbox" name="rememberMe" id="rememberMe"/>
    <label for="rememberMe" style="vertical-align: top">Remember Me</label><br/>
    
    <button type="submit" class="button">Login</button>
</p>
</form>

<script type="text/javascript">
    Form.focusFirstElement($("loginForm"));
</script>
