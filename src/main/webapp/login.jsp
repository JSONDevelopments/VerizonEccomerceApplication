<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign in</title>
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
				<a href="https://www.xadmin.net" class="navbar-brand" style="color: white"> User
					Management Application </a>
			</div>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<form action="user-login" method="post">
					<caption>
						<h2>Login</h2>
					</caption>
					<fieldset class="form-group">
						<label>User Email</label> <input type="email"
							value="<c:out value='${user.email}' />" class="form-control"
							name="email" required="required">
					</fieldset>
					<fieldset class="form-group">
						<label>Password</label> <input type= "password" minlength="8"
							value="<c:out value='${user.password}' />" class="form-control"
							name="password" required="required">
					</fieldset>

					<button type="submit" class="btn btn-primary">Login</button>
					&nbsp;&nbsp;&nbsp;
					<a href ="<%=request.getContextPath()%>/user-new" class = "btn btn-dark">Create New Account</a>
					<br/>
					<p>${errorText}</p>
				</form>
			</div>
		</div>
	</div>
</body>
</html>