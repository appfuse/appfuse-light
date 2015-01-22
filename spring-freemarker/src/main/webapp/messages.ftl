<#-- Success Messages -->
<#if successMessages??>
    <div class="alert alert-success alert-dismissable">
        <a href="#" data-dismiss="alert" class="close">&times;</a>
        <#list successMessages as message>
        ${message?html}<br>
        </#list>
    </div>
</#if>

