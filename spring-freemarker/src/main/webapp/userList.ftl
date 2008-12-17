<title>${rc.getMessage("userList.title")}</title>

<button onclick="location.href='userform.html'" style="float: right; margin-top: -30px; width: 100px">Add User</button>

<table class="table" id="userList">
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
    <td><a href="userform.html?id=${user.id}">${user.id}</a></td>
    <td>${user.firstName?html}</td>
    <td>${user.lastName?html}</td>
    <td>${user.email?html}</td>
</tr>
</#list>
</tbody>
</table>

<script type="text/javascript">highlightTableRows("userList");</script>
