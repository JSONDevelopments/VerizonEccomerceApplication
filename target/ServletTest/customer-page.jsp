<%@page import="com.jason.dao.ProductDao"%>
<%@page import="com.jason.model.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");
    request.setAttribute("dcf", dcf);
    User account = (User) request.getSession().getAttribute("account");
    System.out.println("Cart Page. User name: " + account.getName());
    ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
    List<Cart> cartProduct = null;
    if (cart_list != null) {
        ProductDao pDao = new ProductDao();
        cartProduct = pDao.getCartProducts(cart_list);
        double total = pDao.getTotalCartPrice(cart_list);
        request.setAttribute("total", total);
        request.setAttribute("cart_list", cart_list);
    }
%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="includes/head.jsp"%>
    <title>E-Commerce Cart</title>
</head>
<body>
<%@include file="includes/navbar.jsp"%>

<div class="container">
    <div class="card-header my-3">All Products</div>
    <p>${notifcationText}</p>
    <div class="row">
        <c:forEach var="product" items="${products}">
        <div class="col-lg-3">
            <div style="outline-width: thin; outline-style:solid;outline-color: rgb(228, 228, 237);margin-bottom: 25px" >
                <br>
                <img class="card-img-top" style="height:150px;width:auto; display: block; margin-left: auto;margin-right: auto;" src="${product.imageLocation}"
                     alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">${product.name}</h5>
                    <h6 class="price">Price: $${product.price}</h6>
                    <div class="mt-3 d-flex justify-content-between">
                        <a class="btn btn-dark" href="add-to-cart?id=${product.id}">Add to Cart</a> <a
                            class="btn btn-primary" href="order-now?quantity=1&id=${product.id}">Buy Now</a>
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>
</body>
</html>