<#import "/spring.ftl" as spring/>
<#assign xhtmlCompliant = true in spring>

<head>
    <title><@spring.message "userForm.title"/></title>
</head>

<div class="col-sm-3">
    <h2><@spring.message "userForm.title"/></h2>
    <p>Please fill in user's information.</p>
</div>
<div class="col-sm-6">
    <@spring.bind "user.*"/>
    <#if spring.status.error>
    <div class="alert alert-danger alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <#list spring.status.errorMessages as error>
        ${error}<br/>
        </#list>
    </div>
    </#if>

    <form method="post" action="<@spring.url '/userform'/>" id="userForm" class="well" autocomplete="off">
    <@spring.formHiddenInput "user.id"/>
    <@spring.formHiddenInput "user.version"/>
        <@spring.bind "user.username"/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <label for="username" class="control-label"><@spring.message "user.username"/>:</label>
            <@spring.formInput "user.username", 'id="username" required class="form-control"'/>
            <@spring.showErrors "<br/>", "help-block"/>
        </div>
        <@spring.bind "user.password"/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <label for="password" class="control-label"><@spring.message "user.password"/>:</label>
            <input type="password" id="password" name="password" value="${spring.stringStatusValue}" required class="form-control">
            <@spring.showErrors "<br/>", "help-block"/>
        </div>
        <@spring.bind "user.firstName"/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <label for="firstName" class="control-label"><@spring.message "user.firstName"/>:</label>
            <@spring.formInput "user.firstName", 'id="firstName" class="form-control"'/>
            <@spring.showErrors "<br/>", "help-block"/>
        </div>
        <@spring.bind "user.lastName"/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <label for="lastName" class="control-label"><@spring.message "user.lastName"/>:</label>
            <@spring.formInput "user.lastName", 'id="lastName" class="form-control"'/>
            <@spring.showErrors "<br/>", "help-block"/>
        </div>
        <@spring.bind "user.email"/>
        <div class="form-group<#if spring.status.error> has-error</#if>">
            <label for="email" class="control-label"><@spring.message "user.email"/>:</label>
            <@spring.formInput "user.email", 'id="email" required class="form-control"'/>
            <@spring.showErrors "<br/>", "help-block"/>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary" name="save" id="save">
                <i class="icon-ok icon-white"></i> <@spring.message "button.save"/></button>
            <#if user.id?exists>
                <button type="submit" class="btn btn-danger" name="delete" id="delete">
                    <i class="icon-trash"></i> <@spring.message "button.delete"/></button>
            </#if>
            <a href="${rc.contextPath}/users" class="btn btn-default">
                <i class="icon-remove"></i> <@spring.message "button.cancel"/></a>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("input[type='text']:visible:enabled:first", document.forms['userForm']).focus();
    });
</script>
