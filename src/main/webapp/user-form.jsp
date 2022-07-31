
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>User Management Application</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>

	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: #333232">
			<div>
				<a href="https://www.xadmin.net" class="navbar-brand"
					style="color: white"> User Management Application </a>
			</div>

			<c:if test="${account != null && account.admin == 1}">
				<ul class="navbar-nav">
					<li><a href="<%=request.getContextPath()%>/user-list"
						class="nav-link">Users</a></li>
				</ul>
			</c:if>

			<c:if test="${account != null}">
				<ul class="navbar-nav">
					<li><a href="<%=request.getContextPath()%>/user-logout"
						class="nav-link">Sign Out</a></li>
				</ul>
			</c:if>

		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${user != null}">
					<form action="user-update" method="post">
				</c:if>
				<c:if test="${user == null}">
					<form action="user-insert" method="post">
				</c:if>
					<caption>
						<h2>
							<c:if test="${user != null}">
            			Edit User
            		</c:if>
							<c:if test="${user == null}">
            			Add New User
            		</c:if>
						</h2>
					</caption>

					<c:if test="${user != null}">
						<input type="hidden" name="id"
							value="<c:out value='${user.id}' />" />
					</c:if>

					<fieldset class="form-group">
						<label>Name</label> <input type="text"
							value="<c:out value='${user.name}' />" class="form-control"
							name="name" required="required">
					</fieldset>

					<fieldset class="form-group">
						<label>User Email</label> <input type="email"
							value="<c:out value='${user.email}' />" class="form-control"
							name="email">
					</fieldset>
					<fieldset class="form-group">
						<label>Password</label> <input type="password" minlength="8"
							value="<c:out value='${user.password}' />" class="form-control"
							name="password">
					</fieldset>

					<fieldset class="form-group">
						<label>Billing Address</label> <input type="text"
							value="<c:out value='${user.billingAddress}' />"
							class="form-control" name="billing_address">
					</fieldset>
					<c:if test="${account != null && account.admin == 1}">
						<fieldset class="form-group">
							<label>Admin</label> <input type="text"
								value="<c:out value='${user.admin}' />" class="form-control"
								name="admin">
						</fieldset>
					</c:if>

					<button type="submit" class="btn btn-primary">Save</button>
					<p>${errorText}</p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>