<%
    // remove all users
    users = User.list()
    for (user in users) {
        user.delete()
    }
%>

<g:set var="lastName" value="Raible"/>

<sel:test name="UserTest">
    <g:each var="i" in="${ (0..10) }">
        <sel:row command="open" target="${request.contextPath}"/>
        <sel:row line="clickAndWait|userController"/>
        <sel:row line="clickAndWait|link=New User"/>
        <sel:row command="type" target="lastName" value="${lastName + i}"/>
        <sel:row line="clickAndWait|//input[@value='Create']"/>
        <sel:row line="clickAndWait|link=User List"/>
        <g:if test="${i == 10}">
            <sel:row line="clickAndWait|link=Next"/>
        </g:if>
        <sel:row line="verifyTextPresent|${lastName + i}"/>
    </g:each>
</sel:test>