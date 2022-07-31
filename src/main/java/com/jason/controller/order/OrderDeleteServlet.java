package com.jason.controller.order;

import com.jason.dao.OrderDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/cancel-order")
public class OrderDeleteServlet extends HttpServlet {
    private OrderDao orderDao;
    public OrderDeleteServlet() {
        orderDao = new OrderDao();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderDeleteServet doGet called.");
        response.getWriter().append("Served at: ").append(request.getContextPath());

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            orderDao.deleteOrder(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-orders");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
