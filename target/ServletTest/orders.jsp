<%@page import="java.text.DecimalFormat" %>
<%@page import="com.jason.dao.OrderDao" %>
<%@page import="com.jason.dao.ProductDao" %>
<%@page import="com.jason.model.*" %>
<<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/includes/head.jsp" %>
    <title>E-Commerce Cart</title>
</head>
<body>
<%@include file="/includes/navbar.jsp" %>
<div class="container">
    <div class="card-header my-3">All Orders</div>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th scope="col">Date</th>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Quantity</th>
            <th scope="col">Price</th>
            <th scope="col">Status</th>
            <th scope="col">Cancel</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="order" items="${orders}">
        <tr>
            <td><c:out value="${order.date}"/></td>
            <td><c:out value="${order.orderId}"/></td>
            <td><c:out value="${productMap.get(order.getProductId()).getName()}"/></td>
            <td><c:out value="${order.quantity}"/></td>
            <td><c:out value="$ ${dcf.format(productMap.get(order.getProductId()).getPrice()*order.quantity)}"/></td>
            <td><c:out value="${order.status}"/></td>
            <td><a class="btn btn-sm btn-outline-danger" href="cancel-order?id=${order.orderId}">Cancel Order</a></td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>