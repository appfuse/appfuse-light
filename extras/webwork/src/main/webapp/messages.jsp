<%-- Error Messages --%>
<ww:if test="hasFieldErrors()">
    <div class="error">	
      <ww:iterator value="fieldErrors">
          <ww:iterator value="value">
             <ww:property/><br/>
          </ww:iterator>
      </ww:iterator>
    </div>
</ww:if>

<%-- Success Messages --%>
<c:if test="${not empty message}">
    <div class="message"><c:out value="${message}"/></div>
</c:if>
