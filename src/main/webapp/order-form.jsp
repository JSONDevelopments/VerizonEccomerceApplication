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
                <li><a href="<%=request.getContextPath()%>/order-list"
                       class="nav-link">Orders</a></li>
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
            <form action="order-update" method="post">
                    <caption>
                        <h2>
                            Edit Status
                        </h2>
                    </caption>

                    <c:if test="${order != null}">
                        <input type="hidden" name="id"
                               value="<c:out value='${order.orderId}' />" />
                    </c:if>

                    <fieldset class="form-group">
                        <label>Status</label> <input type="text"
                                                   value="<c:out value='${order.status}' />" class="form-control"
                                                   name="status" required="required">
                    </fieldset>


                    <button type="submit" class="btn btn-primary">Save</button>
                    <p>${errorText}</p>
                </form>
        </div>
    </div>
</div>
</body>
</html>