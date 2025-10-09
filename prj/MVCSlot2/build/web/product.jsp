<%-- 
    Document   : Product
    Created on : Feb 29, 2024, 1:53:08 PM
    Author     : viett
--%>

<%@page import="viettl.product.ProductDao"%>
<%@page import="viettl.product.ProductDto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Store</title>
    </head>

    <body>
        <h1>Book Store</h1>
        <c:set var="lastChooseItem" value="${param.cboBook}"/>
        
        <form action="DispatchServlet">
            choose <select name="cboBook" value="${lastChooseItem}">
                <c:forEach var="item" items="${requestScope.productList}">
                    <option value="${item.productId}">${item.name}</option>
                </c:forEach>
            </select>
            </br>
            <input type="submit" value="Add Book To Your Cart" name="action" />
            <input type="submit" value="View Your Cart" name="action" />
        </form>
    </body>
</html>
