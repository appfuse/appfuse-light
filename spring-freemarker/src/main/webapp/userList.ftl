<title>${rc.getMessage("userList.title")}</title>
<h2>${rc.getMessage("userList.title")}</h2>

<button class="btn btn-primary" onclick="location.href='userform'" style="float: right; margin-top: -30px">
    <i class="icon-plus icon-white"></i> Add User</button>

<table class="table table-condensed table-striped table-hover" id="userList">
<thead>
<tr>
    <th>${rc.getMessage("user.id")}</th>
    <th>${rc.getMessage("user.firstName")}</th>
    <th>${rc.getMessage("user.lastName")}</th>
    <th>${rc.getMessage("user.email")}</th>
</tr>
</thead>
<tbody>
<#list userList as user>
<#if user_index % 2 == 0> <tr class="odd">
<#else> <tr class="even">
</#if>
    <td><a href="userform?id=${user.id}">${user.id}</a></td>
    <td>${user.firstName?html}</td>
    <td>${user.lastName?html}</td>
    <td>${user.email?html}</td>
</tr>
</#list>
</tbody>
</table>
