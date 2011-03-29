<%-- Error Messages --%>
<s:if test="hasFieldErrors()">
    <div class="error">	
      <s:iterator value="fieldErrors">
          <s:iterator value="value">
             <s:property/><br/>
          </s:iterator>
      </s:iterator>
    </div>
</s:if>

<%-- Success Messages --%>
<c:if test="${not empty messages}">
    <div class="message" id="successMessages">
        <c:forEach var="msg" items="${messages}">
            <c:out value="${msg}"/><br />
        </c:forEach>
    </div>
    <c:remove var="messages" scope="session"/>
</c:if>
