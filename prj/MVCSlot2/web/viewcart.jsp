<%-- 
    Document   : ViewCart
    Created on : Feb 26, 2024, 4:53:21 PM
    Author     : viett
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="viettl.product.ProductDto"%>
<%@page import="viettl.product.ProductDao"%>
<%@page import="java.util.Map"%>
<%@page import="viettl.cart.ShoppingCart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
    </head>
    <body>
        <h1>Book Store</h1>
        <c:set var="productList" value="${requestScope.productList}"/>
        <c:if test="${not empty productList}">
            <form action="DispatchServlet">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Name</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${productList}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${product.productName}</td>
                                <td>${product.quantity}</td>
                                <td>
                                    <input type="checkbox" name="remove_box" value="${product.productId}"/>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="3">
                                <a href="DispatchServlet?action=Shopping">Add More Books to Your Cart </a>
                            </td>
                            <td>
                                <input type="submit" value="Remove Selected Items" name = "action"/>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br/>
                <input type="submit" value="Buy" name="action" />
            </form>
        </c:if>

        <c:if test="${empty productList}">
            <h2>
                <font color="red">
                    No cart is existed!!!
                </font>
            </h2>
        </c:if>
    </body>
</html>
