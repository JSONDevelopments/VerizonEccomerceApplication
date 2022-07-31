package com.jason.controller.order;

import com.jason.dao.OrderDao;
import com.jason.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/order-insert")
public class OrderInsertServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderInsertServlet() {
        super();
        orderDao = new OrderDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderInsertServlet doGet called.");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        int userId = Integer.parseInt(request.getParameter("userId"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Date date = new Date();
        String orderDate = formatter.format(date);
        String status = request.getParameter("status");
        System.out.println("Attempting to insert: " + userId + " " + productId);
        Order newOrder = new Order(userId, productId, quantity, orderDate, status);
        orderDao.insertOrder(newOrder);


    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
