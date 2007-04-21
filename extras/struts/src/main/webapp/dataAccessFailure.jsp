<%@ include file="/taglibs.jsp" %>

<h3>Data Access Failure</h3>
<p>
    <c:out value="${requestScope['org.apache.struts.action.EXCEPTION'].message}"/>
</p>

<!--
<% 
Exception ex = (Exception) request.getAttribute("org.apache.struts.action.EXCEPTION");
ex.printStackTrace(new java.io.PrintWriter(out)); 
%>
-->

<a href="<c:url value='/'/>" onclick="history.back(); return false">&#171; Back</a>
