<%@ include file="/taglibs.jsp"%>

<title>Login</title>

<p>
    Please enter your username and password to login.
    User user/user has a <strong>ROLE_USER</strong> role,
    while admin/admin has an <strong>ROLE_ADMIN</strong> role. These users
    and their encrypted passwords are stored in <em>WEB-INF/security.xml</em>.
</p>

<form action="<c:url value="/j_security_check"/>" id="loginForm" method="post">
<p>
    <c:if test="${param.error == 'true'}">
        <div class="error">${sessionScope.SPRING_SECURITY_LAST_EXCEPTION_KEY.message}</div>
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
