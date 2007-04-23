<%@ include file="/taglibs.jsp" %>

<title>My First Stripe</title>

<p>
    Hi, I'm the Stripes Calculator. I can only do addition. Maybe, some day, a nice programmer
    will come along and teach me how to do other things?
</p>

<stripes:form action="/calculator.html" focus="">
    <table class="detail" style="width: 250px">
        <tr>
            <td>Number 1:</td>
            <td>
                <stripes:text name="numberOne"/>
            </td>
        </tr>
        <tr>
            <td>Number 2:</td>
            <td>
                <stripes:text name="numberTwo"/>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <stripes:submit name="addition" value="Add"/>
            </td>
        </tr>
        <tr>
            <td>Result:</td>
            <td>${actionBean.result}</td>
        </tr>
    </table>
</stripes:form>
