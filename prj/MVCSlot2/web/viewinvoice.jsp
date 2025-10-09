<%-- 
    Document   : vieworder.jsp
    Created on : Mar 12, 2024, 4:36:31 AM
    Author     : viett
--%>

<%@page import="viettl.order.OrderDto"%>
<%@page import="viettl.orderdetail.OrderDetailDto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order</title>
        <style>
            th {
                padding: 5px 10px
            }
            .table-content td {
                padding: 5px 10px
            }
        </style>
    </head>
    <body>
        <h1>Invoice Page</h1>
        <c:set var="invoice" value="${requestScope.orderDto}"/>
        <c:if test="${not empty invoice}">
            <table border="1" >
                <tbody >
                    <tr>
                        <td>Invoice id</td>
                        <td>${invoice.orderId}</td>
                    </tr>
                    <tr>
                        <td>User address</td>
                        <td>${invoice.userAddress}</td>
                    </tr>
                    <tr style="width: 100%">
                        <td>Time order</td>
                        <td>${invoice.datetime}</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <table class="table-content" border="0">
                                <thead>
                                    <tr>
                                        <th>No. </th>
                                        <th>Product name</th>
                                        <th>Unit price</th>
                                        <th>Quantity</th>
                                        <th>Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:set var="itemList" value="${invoice.itemList}"/>
                                    <c:forEach var="item" items="${itemList}" varStatus="counter">
                                        <tr>
                                            <td>${counter.count}</td>
                                            <td>${item.productName}</td>
                                            <td style="text-align: center">${item.unitPrice}</td>
                                            <td style="text-align: center">${item.quantity}</td>
                                            <td style="text-align: center">${item.total}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>Total</td>
                        <td style="text-align: center">${invoice.total}</td>
                    </tr>
                </tbody>

            </table>
        </c:if>
        
        <c:if test="${empty invoice}">
             <h2 color="red"> 
                Invoice is not found!!!
            </h2>
        </c:if>
        <br/>
         <a href="DispatchServlet?action=Shopping">Go to Shopping</a>
    </body>
</html>
