<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<h3 class = "text-center">List of Users</h3>
			<hr>
			<div class = "container text-left">
				<a href = "<%=request.getContextPath()%>/user-new" class = "btn btn-primary">Add New User</a>
			</div>
			<br>
			<table class = "table table-bordered">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Email</th>
						<th>Password</th>
						<th>Billing Address</th>
						<th>Admin</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${listUser}">
						<tr>
							<td><c:out value="${user.id}"/></td>
							<td><c:out value="${user.name}"/></td>
							<td><c:out value="${user.email}"/></td>
							<td><c:out value="${user.password.substring(0,12)}"/></td>
							<td><c:out value="${user.billingAddress}"/></td>
							<td><c:out value="${user.admin}"/></td>
							<td><a class="btn btn-sm btn-outline-primary" href="user-edit?id=<c:out value='${user.id}'/>">Edit</a>
								&nbsp;&nbsp;&nbsp;&nbsp; <a class="btn btn-sm btn-outline-danger" href="user-delete?id=<c:out value='${user.id}'/>">Delete</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>