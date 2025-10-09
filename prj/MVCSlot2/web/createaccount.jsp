<%-- 
    Document   : CreateAccount
    Created on : Mar 7, 2024, 4:01:30 PM
    Author     : viett
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Create</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Registration</h1>
        <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.createErrors}"/>
            Username* <input type="text" name="input_username" value="${param.input_username}" />  </br>
            
            <c:if test="${not empty errors.usernameLengthError}">
                <font color="red">
                    ${errors.usernameLengthError}
                </font><br/>
            </c:if>
            <c:if test="${not empty errors.usernameIsExisted}">
                <font color="red">
                    ${errors.usernameIsExisted}
                </font><br/>
            </c:if>
            
            Password* <input type="password" name="input_password" value="" />(6 - 30 chars)<br/>
            <c:if test="${not empty errors.passwordLengthError}">
                <font color="red">
                    ${errors.passwordLengthError}
                </font><br/>
            </c:if>
                Confirm* <input type="text" name="input_confirm" value="" /><br/>
            <c:if test="${not empty errors.confirmNotmatched}">
                <font color="red">
                    ${errors.confirmNotmatched}
                </font><br/>
            </c:if>
                Full name* <input type="text" name="input_fullname" value="${param.input_fullname}" />(2 - 50 chars)<br/>
            <c:if test="${not empty errors.fullnameLengthError}">
                <font color="red">
                    ${errors.fullnameLengthError}
                </font><br/>
            </c:if>
                
            <input type="submit" value="Create New Account" name="action"/>
            <input type="reset" value="Reset" />
        </form>
    </body>
</html>
