<%@ include file="/taglibs.jsp"%>

<head>
    <title><fmt:message key="userForm.title"/></title>
    <%-- Calendar Setup - put in decorator if needed in multiple pages --%>
    <link href="${ctx}/styles/calendar.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/scripts/calendar.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/calendar-setup.js"></script>
    <script type="text/javascript" src="${ctx}/scripts/lang/calendar-en.js"></script>
    <%-- DWR Setup - put in decorator if needed in multiple pages --%>
    <script type="text/javascript" src="${ctx}/dwr/interface/UserManager.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/engine.js"></script>
    <script type="text/javascript" src="${ctx}/dwr/util.js"></script>
</head>

<p ${(not empty param.ajax) ? 'style="display:none"' : ""}>This page uses DWR to save the user and do an in-page-update.  The effects
showing the success message and then dropping it off the screen is done with script.aculo.us.</p>

<script type="text/javascript">
function fillForm(user) {
    DWRUtil.setValues(user);
    new Effect.Highlight('firstName');

    fields = Form.getInputs(document.forms['userForm'], "text");
    for (i=0; i < fields.length; i++) {
        new Effect.Highlight(fields[i].id);
    }
}

function saveUser() {
    if (validateUser(document.forms['userForm'])) {
        user = { id:"", firstName:"", lastName:"", birthday:"" };
        DWRUtil.getValues(user);
        if (user.id == "") user.id = null;
        if (user.birthday == "") user.birthday = null;
        else user.birthday = Date.parse(user.birthday);
        if (isNaN(user.birthday)) {
            alert(DWRUtil.getValue('birthday') + " is not a valid date, please try again.");
            return false;
        }
        UserManager.saveUser(showSuccessMessage, user);
    }
}

function showSuccessMessage() {
    var div = document.getElementById("messages");
    if (div == null) {
        div = document.createElement("div");
        div.id = "messages";
        $('info').appendChild(div);
        div.className = "message";
    } else {
        Element.show('messages');
    }
    div.innerHTML = "User saved successfully.";
    new Effect.Highlight('messages');
    window.setTimeout("Effect.DropOut('messages')", 1000);
}

</script>
<p id="info">Please fill in user's information below:</p>

<form:form commandName="user" method="post" action="userform.html" onsubmit="return validateUser(this)" id="userForm">
<form:errors path="*" cssClass="error"/>
<form:hidden path="id" />
<table class="detail">
<tr>
    <th><label for="firstName"><fmt:message key="user.firstName"/>:</label></th>
    <td>
        <form:input path="firstName" id="firstName"/>
        <form:errors path="firstName" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="firstName" class="required">* <fmt:message key="user.lastName"/>:</label></th>
    <td>
        <form:input path="lastName" id="firstName"/>
        <form:errors path="lastName" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="birthday"><fmt:message key="user.birthday"/>:</label></th>
    <td>
        <form:input path="birthday" id="birthday" size="11"/>
            <button id="birthdayCal" type="button" class="button"> ... </button> [<fmt:message key="date.format"/>]
        <form:errors path="birthday" cssClass="fieldError"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <input type="submit" class="button" name="save" value="Save" onclick="saveUser();this.blur();return false"/>
      <c:if test="${not empty param.id}">
        <input type="submit" class="button" name="delete" value="Delete"/>
      </c:if>
      <c:if test="${not empty param.ajax}">
          <a href="#" class="lbAction" rel="deactivate">Cancel</a>
      </c:if>
      <c:if test="${empty param.ajax}">
          <input type="submit" class="button" name="cancel" value="Cancel" onclick="bCancel=true"/>
      </c:if>
    </td>
</tr>
</table>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('userForm'));
    Calendar.setup(
    {
        inputField  : "birthday",      // id of the input field
        ifFormat    : "%m/%d/%Y",      // the date format
        button      : "birthdayCal"    // id of the button
    }
    );
</script>

<v:javascript formName="user" staticJavascript="false" xhtml="true" cdata="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
