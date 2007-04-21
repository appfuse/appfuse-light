<%-- Success Messages --%>
<c:if test="${not empty message}">
    <div class="message" id="messages">${message}</div>
    <script type="text/javascript">
        new Effect.Highlight('messages');
        window.setTimeout("Effect.DropOut('messages')", 1000);
    </script>
</c:if>
