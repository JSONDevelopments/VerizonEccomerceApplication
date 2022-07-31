
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
					<li><a href="<%=request.getContextPath()%>/product-list"
						class="nav-link">Products</a></li>
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
				<c:if test="${product != null}">
					<form action="product-update" method="post">
				</c:if>
				<c:if test="${product == null}">
					<form action="product-insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${product != null}">
            				Edit Product
            			</c:if>
						<c:if test="${product == null}">
            				Add New Product
            			</c:if>
					</h2>
				</caption>

				<c:if test="${product != null}">
					<input type="hidden" name="id"
						value="<c:out value='${product.id}' />" />
				</c:if>


					<fieldset class="form-group">
						<label>Image Location</label> <input type="text"
													  value="<c:out value='${product.imageLocation}' />" class="form-control"
													  name="imageLocation" required="required">
					</fieldset>
				<fieldset class="form-group">
					<label>Product</label> <input type="text"
						value="<c:out value='${product.name}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Description</label> <input type="text"
						value="<c:out value='${product.description}' />"
						class="form-control" name="description">
				</fieldset>
				<fieldset class="form-group">
					<label>Price</label> <input type="text"
						value="<c:out value='${product.price}' />" class="form-control"
						name="price">
				</fieldset>

				<fieldset class="form-group">
					<label>Stock</label> <input type="text"
						value="<c:out value='${product.stock}' />" class="form-control"
						name="stock">
				</fieldset>

				<button type="submit" class="btn btn-primary">Save</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>