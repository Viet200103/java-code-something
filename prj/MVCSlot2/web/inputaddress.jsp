<%-- 
    Document   : inputaddress.jsp
    Created on : Mar 12, 2024, 10:51:57 PM
    Author     : viett
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User address</title>
    </head>
    <body>
        <form action="InputAddressServlet" method="POST">
            User address <input type="text" name="user-address" value="" /> <br/>
            
            <c:set var="error" value="${requestScope.addressError}"/>
            <c:if test="${not empty error}">
                <font color="red">
                    ${error.addressLengthError}
                </font><br/>
            </c:if>
            
            <input name="address-action" type="submit" value="cancel" /> 
            <input name="address-action" type="submit" value="complete" />
        </form>
        
    </body>
</html>
