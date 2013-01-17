<#import "/spring.ftl" as spring/>
<#assign xhtmlCompliant = true in spring>

<head>
    <title><@spring.message "userForm.title"/></title>
</head>

<p>Please fill in user's information below:</p>

<@spring.bind "user.*"/>
<#if spring.status.error>
<div class="alert alert-error fade in">
    <a href="#" data-dismiss="alert" class="close">&times;</a>
    <#list spring.status.errorMessages as error>
    ${error}<br/>
    </#list>
</div>
</#if>

<form method="post" action="<@spring.url '/userform'/>" id="userForm" class="well form-horizontal" autocomplete="off">
<@spring.formHiddenInput "user.id"/>
<@spring.formHiddenInput "user.version"/>
    <@spring.bind "user.username"/>
    <div class="control-group<#if spring.status.error> error</#if>">
        <label for="username" class="control-label"><@spring.message "user.username"/>:</label>
        <div class="controls">
            <@spring.formInput "user.username", 'id="username" required'/>
            <@spring.showErrors "<br/>", "help-inline"/>
        </div>
    </div>
    <@spring.bind "user.password"/>
    <div class="control-group<#if spring.status.error> error</#if>">
        <label for="password" class="control-label"><@spring.message "user.password"/>:</label>
        <div class="controls">
            <input type="password" id="password" name="password" value="${spring.stringStatusValue}" required>
            <@spring.showErrors "<br/>", "help-inline"/>
        </div>
    </div>
    <@spring.bind "user.firstName"/>
    <div class="control-group<#if spring.status.error> error</#if>">
        <label for="firstName" class="control-label"><@spring.message "user.firstName"/>:</label>
        <div class="controls">
            <@spring.formInput "user.firstName", 'id="firstName"'/>
            <@spring.showErrors "<br/>", "help-inline"/>
        </div>
    </div>
    <@spring.bind "user.lastName"/>
    <div class="control-group<#if spring.status.error> error</#if>">
        <label for="lastName" class="control-label"><@spring.message "user.lastName"/>:</label>
        <div class="controls">
            <@spring.formInput "user.lastName", 'id="lastName"'/>
            <@spring.showErrors "<br/>", "help-inline"/>
        </div>
    </div>
    <@spring.bind "user.email"/>
    <div class="control-group<#if spring.status.error> error</#if>">
        <label for="email" class="control-label"><@spring.message "user.email"/>:</label>
        <div class="controls">
            <@spring.formInput "user.email", 'id="email" required'/>
            <@spring.showErrors "<br/>", "help-inline"/>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary" name="save" id="save">
            <i class="icon-ok icon-white"></i> Save</button>
        <#if user.id?exists>
            <button type="submit" class="btn" name="delete" id="delete">
                <i class="icon-trash"></i> Delete</button>
        </#if>
        <a href="${rc.contextPath}/users" class="btn">
            <i class="icon-remove"></i> Cancel</a>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        $("input[type='text']:visible:enabled:first", document.forms['userForm']).focus();
    });
</script>
