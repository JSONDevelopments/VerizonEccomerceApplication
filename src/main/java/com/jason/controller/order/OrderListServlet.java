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

@WebServlet("/order-list")
public class OrderListServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;
    ProductDao productDao;
    HttpSession session;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderListServlet() {
        super();
        System.out.println("Order list Servlet Constructor called.");
        orderDao = new OrderDao();
        productDao = new ProductDao();

        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderListServlet called");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        session = request.getSession();
        List<Order> orders = orderDao.selectAllOrders();
        request.setAttribute("orders", orders);
        Map<Integer, Product> productMap = new HashMap<Integer,Product>();
        for(Order order : orders) {
            Product product = productDao.selectProduct(order.getProductId());
            productMap.put(order.getProductId(),product);
        }
        request.setAttribute("productMap",productMap);
        RequestDispatcher dispatcher = request.getRequestDispatcher("order-list.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
