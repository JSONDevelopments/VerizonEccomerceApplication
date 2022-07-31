package com.jason.controller.order;

import com.jason.dao.OrderDao;
import com.jason.dao.ProductDao;
import com.jason.model.Order;
import com.jason.model.Product;
import com.jason.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/user-orders")
public class UserOrdersServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;
    ProductDao productDao;
    HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserOrdersServlet() {
        super();
        System.out.println("User Orders Servlet Constructor called.");
        orderDao = new OrderDao();
        productDao = new ProductDao();

        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserOrdersServlet doGet called.");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        session = request.getSession();
        User account = (User) session.getAttribute("account");
        System.out.println("Searching for user id in orders");
        List<Order> orders = orderDao.selectOrdersByUserId(account.getId());
        System.out.println("Orders for user id found. " + orders.size());
        request.setAttribute("orders", orders);
        Map<Integer, Product> productMap = new HashMap<Integer,Product>();
        for(Order order : orders) {
            Product product = productDao.selectProduct(order.getProductId());
            productMap.put(order.getProductId(),product);
            System.out.println("Added Product " + product.getName() + " to productmap.");
        }
        request.setAttribute("productMap",productMap);
        System.out.println(productMap);
        System.out.println("Orders and ProductMap set. Redirecting to orders.jsp...");
        RequestDispatcher dispatcher = request.getRequestDispatcher("orders.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
