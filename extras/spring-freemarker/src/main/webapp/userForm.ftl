<#import "/spring.ftl" as spring/>
<#assign xhtmlCompliant = true in spring>

<head>
    <title><@spring.message "userForm.title"/></title>
    <link  href="styles/calendar.css"  type="text/css"  rel="stylesheet"/>
    <script type="text/javascript" src="scripts/calendar.js"></script>
    <script type="text/javascript" src="scripts/calendar-setup.js"></script>
    <script type="text/javascript" src="scripts/lang/calendar-en.js"></script>
</head>

<@spring.bind "user.*"/> 
<#if spring.status.error>
<div class="error">
    <#list spring.status.errorMessages as error>
        ${error}<br/>
    </#list>
</div>
</#if>

<p>Please fill in user's information below:</p>
  
<form method="post" action="<@spring.url '/userform.html'/>" id="userForm">
<@spring.formHiddenInput "user.id"/>
<table>
<tr>
    <th><label for="firstName"><@spring.message "user.firstName"/>:</label></th>
    <td>
        <@spring.formInput "user.firstName", 'id="firstName"'/>
        <@spring.showErrors "<br>", "fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="lastName"><@spring.message "user.lastName"/>:</label></th>
    <td>
        <@spring.formInput "user.lastName", 'id="lastName"'/>
        <@spring.showErrors "<br>", "fieldError"/>
    </td>
</tr>
<tr>
    <th><label for="birthday"><@spring.message "user.birthday"/>:</label></th>
    <td>
        <@spring.formInput "user.birthday", 'id="birthday" size="11"'/> 
        <button id="birthdayCal" type="button" class="button"> ... </button> [<@spring.message "date.format"/>]
        <@spring.showErrors "<br>", "fieldError"/>
    </td>
</tr>
<tr>
    <td></td>
    <td>
        <input type="submit" class="button" name="save" value="Save"/>
      <#if user.id?exists>
        <input type="submit" class="button" name="delete" value="Delete"/>
      </#if>
        <input type="submit" class="button" name="cancel" value="Cancel"/>
    </td>
</table>
</form>

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
