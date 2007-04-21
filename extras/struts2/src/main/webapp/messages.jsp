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
<c:if test="${not empty message}">
    <div class="message">${message}</div>
</c:if>
