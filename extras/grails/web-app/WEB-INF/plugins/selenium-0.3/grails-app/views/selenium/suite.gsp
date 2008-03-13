<html>
<head>
<script>
	function load(dir) {
		var url = window.top.location.href;
		url = url.substring(0,url.indexOf('selenium/suite'))+'selenium/suite';
		if(dir.length>0) {
			url += '?dir%3d'+dir;
		}
		window.top.location.href=url;
	}
</script>
</head>
<body>

	
	<div style="width:100%;background-color:#efefef;padding:3px;" id="suites">
		<b>Suites</b>
		<p style="margin-left:5px;">
		<a href="#" onclick="load('')">All tests</a><br/>
		<g:each var="item" in="${suites}">
			<g:each var="step" in="${item.split('/')}">&nbsp;&nbsp;</g:each>
	        <a href="#" onclick="load('${item.replace('\\','/')}')"><% if(item.indexOf('/')>-1) out << item.substring(item.lastIndexOf('/')+1) else out << item %></a><br/>
		</g:each>
		</p>
	</div>
<br/>

    <table id="suiteTable" cellpadding="1" cellspacing="1" border="1" style="width:100%">
        <tbody>
        <tr><td><b>Tests (${params.dir})</b></td> </tr>
		<g:each var="item" in="${tests}">
			<g:if test="${item.endsWith('.psv')}">
		        <tr><td><a href="view?file=${item}">${item}</a></td></tr>
			</g:if>
			<g:else>
		        <tr><td><a href="tests/${item.replace('\\','/')}">${item}</a></td></tr>
			</g:else>
		</g:each>
        </tbody>
    </table>
</body>
</html>

