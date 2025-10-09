<%-- 
    Document   : search.jsp
    Created on : Jan 29, 2024, 4:54:58 PM
    Author     : viett
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="viettl.registration.RegistrationDto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <font color="red">
            Welcome, ${sessionScope.userInfo.fullName}
        </font> <br/>
        <a href="DispatchServlet?action=Logout">Logout</a>

        <h1>Search Last Name</h1>

        <form action="DispatchServlet">
            Search Value <input name="input_name" value="${param.input_name}" type="text"/> </br>
            <input name="action" type="submit" value="Search"/>
        </form>
        <br/>

        <c:set var="searchValue" value="${param.input_name}"/>
        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:if test="${not empty result}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                            <!--<th></th>-->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="user" items="${result}" varStatus="counter"> 
                        <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>${counter.count}</td>
                                <td>
                                    ${user.username}
                                    <input type="hidden" name="input_username" value="${user.username}" />
                                </td>
                                <td>
                                    <input type="text" name="input_password" value="${user.password}" />
                                </td>
                                <td>${user.fullName}</td>
                                <td>
                                    <input type="checkbox" name="roleCheckbox" value="ON" 
                                           <c:if test="${user.role}">
                                               checked="checked"
                                           </c:if>
                                    />
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="DispatchServlet">
                                        <c:param name="action" value="Delete"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                        <c:param name="pimary_key" value="${user.username}"/>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                                
                                <td>
                                    <input type="submit" name="action" value="Update" />
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                </td>
                            </tr>
                        </form>
                        </c:forEach>
                    </tbody>
                </table>

<!--                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Username</th>
                            <th>Password</th>
                            <th>Full name</th>
                            <th>Role</th>
                            <th>Delete</th>
                            <th>Update</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="registrationDTO" items="${result}" varStatus="counter">
                        <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>${counter.count}.</td>
                                <td>
                                    ${registrationDTO.username}
                                    <input type="hidden" name="input_username" value="${registrationDTO.username}"/>
                                </td>
                                <td>
                                    <input type="text" name="input_password" value="${registrationDTO.password}"/>
                                </td>
                                <td>${registrationDTO.fullName}</td>
                                <td>
                                    <input type="checkbox" name="checkbox_admin" value="ON"
                                           <c:if test="${registrationDTO.role}">
                                               checked="checked"
                                           </c:if>
                                    />
                                </td>
                                <td>
                                    <c:url var="deleteLink" value="DispatchServlet">
                                        <c:param name="action" value="Delete"/>
                                        <c:param name="pimary_key" value="${registrationDTO.username}"/>
                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                    </c:url>
                                    <a href="${deleteLink}">Delete</a>
                                </td>
                                <td>
                                    <input value="Update" name="action" type="submit"/>
                                    <input value="${searchValue}" name="lastSearchValue" type="hidden"/>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>-->

        </c:if>
        <c:if test="${empty result}">
            <h2 style="color: red"> 
                No record is matched!!!
            </h2>
        </c:if>
    </c:if>

</body>
</html>
