<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<nav class="navbar navbar-expand-md navbar-dark"
     style="background-color: #333232">
    <div>
        <a href="https://www.xadmin.net" class="navbar-brand"
           style="color: white"> User Management Application </a>
    </div>
    <ul class = "navbar-nav">
        <li><a href="./cart.jsp" class = "nav-link">Cart <span class="badge badge-danger">${cart_list.size()}</span> </a></li>
    </ul>

    <ul class="navbar-nav">
        <li><a href="<%=request.getContextPath()%>/customer-page"
               class="nav-link">Products</a></li>
    </ul>

    <c:if test="${account != null}">
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/user-orders"
                   class="nav-link">Orders</a></li>
        </ul>

        <ul class="navbar-nav">
            <li>
                <a href="<%=request.getContextPath()%>/user-logout"
                   class="nav-link">Sign Out</a></li>
        </ul>
    </c:if>

    <c:if test="${account == null}">
        <ul class="navbar-nav">
            <li>
                <a href="<%=request.getContextPath()%>/user-login"
                   class="nav-link">Sign In</a></li>
        </ul>
    </c:if>
</nav>