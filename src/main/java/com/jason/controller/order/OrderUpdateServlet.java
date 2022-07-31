package com.jason.controller.order;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.model.Order;
import com.jason.dao.OrderDao;

/**
 * Servlet implementation class OrderUpdateServlet
 */
@WebServlet("/order-update")
public class OrderUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderUpdateServlet() {
        super();
        orderDao = new OrderDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderUpdateServlet doGet called.");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        System.out.println("Getting order id...");
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println("order id = " + id + ". Now getting status...");
        String status = request.getParameter("status");
        System.out.println("Trying to update order " + id + " to " + status);
        try {
            orderDao.updateOrder(id, status);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response.sendRedirect("order-list");
        }
        System.out.println("Redirecting to order-list servlet");
        response.sendRedirect("order-list");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
