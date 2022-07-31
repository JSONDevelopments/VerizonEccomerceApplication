<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    %>
<!DOCTYPE html>
<html>
<head>
    <title>Management Application</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous"></head>
<body>
<header>
    <nav class="navbar navbar-expand-md navbar-dark"
         style="background-color: #333232">
        <div>
            <a href="https://www.xadmin.net" class="navbar-brand"
               style="color: white"> User Management Application </a>
        </div>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/user-list"
                   class="nav-link">Users</a></li>
        </ul>

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/product-list"
                   class="nav-link">Products</a></li>
        </ul>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/order-list"
                   class="nav-link">Orders</a></li>
        </ul>

        <c:if test="${account != null}">
            <ul class="navbar-nav">
                <li>
                    <a href="<%=request.getContextPath()%>/user-logout"
                       class="nav-link">Sign Out</a></li>
            </ul>
        </c:if>
    </nav>
</header>
<br>

<div class="row">
    <!-- <div class"alert alert-success" *ngIf='message'>{{message}}</div> -->

    <div class = "container">
        <h3 class = "text-center">List of Orders</h3>
        <hr>
        <br>
        <table class = "table table-bordered">
            <thead>
            <tr>
                <th>Date</th>
                <th>ID</th>
                <th>UserId</th>
                <th>Product</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td><c:out value="${order.date}"/></td>
                    <td><c:out value="${order.orderId}"/></td>
                    <td><c:out value="${order.userId}"/></td>
                    <td><c:out value="${productMap.get(order.getProductId()).getName()}"/></td>
                    <td><c:out value="${order.quantity}"/></td>
                    <td><c:out value="$ ${dcf.format(productMap.get(order.getProductId()).getPrice()*order.quantity)}"/></td>
                    <td><c:out value="${order.status}"/></td>
                    <td><a class="btn btn-sm btn-outline-primary" href="order-edit-status?id=${order.orderId}">Edit</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>