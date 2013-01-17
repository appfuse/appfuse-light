<%@ include file="/taglibs.jsp"%>

<title>Login</title>
<body id="login">
<p>
    Please enter your username and password to login.
    User user/user has a <strong>ROLE_USER</strong> role,
    while admin/admin has an <strong>ROLE_ADMIN</strong> role. These users
    and their encrypted passwords are stored in <em>WEB-INF/security.xml</em>.
</p>

<form method="post" id="loginForm" action="<c:url value='/j_security_check'/>" class="form-signin" autocomplete="off">
    <h2 class="form-signin-heading">Sign In</h2>
<c:if test="${param.error != null}">
    <div class="alert alert-error fade in">
        ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</c:if>
    <input type="text" name="j_username" id="j_username" class="input-block-level"
           placeholder="Username" required tabindex="1">
    <input type="password" class="input-block-level" name="j_password" id="j_password" tabindex="2"
           placeholder="Password" required>

    <label class="checkbox" for="rememberMe">
        <input type="checkbox" class="checkbox" name="_spring_security_remember_me" id="rememberMe" tabindex="3"/>
        Remember Me
    </label>

    <input type="submit" class="btn btn-large btn-primary" name="login" id="login" tabindex="4" value="Login">
</form>

<script type="text/javascript">
    $(document).ready(function() {
        $("input[type='text']:visible:enabled:first", document.forms['loginForm']).focus();
    });
</script>
</body>

