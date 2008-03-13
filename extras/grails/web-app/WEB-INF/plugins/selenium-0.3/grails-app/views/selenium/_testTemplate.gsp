<html>
<body>
    <table cellpadding="1" cellspacing="1" border="1">
        <tr><td colspan="3"><b>${name}</b> </td> </tr>
		<g:each var="row" in="${rows}">
			<tr>
			<g:each var="cell" in="${row.tokenize('|')}">
				<td>${cell}</td>
			</g:each>
			<g:set var="cellCount" value="${row.tokenize('|').size()}"/>
			<g:while test="${cellCount++<3}">
				<td></td>
			</g:while>
			</tr>
		</g:each>
    </table>
</body>
</html>

