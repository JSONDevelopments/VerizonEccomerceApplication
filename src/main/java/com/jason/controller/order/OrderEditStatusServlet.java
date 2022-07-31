package com.jason.controller.order;

import com.jason.dao.OrderDao;
import com.jason.dao.UserDao;
import com.jason.model.Order;
import com.jason.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/order-edit-status")
public class OrderEditStatusServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderEditStatusServlet() {
        super();
        orderDao = new OrderDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderEditStatusServlet doGet called.");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        int id = Integer.parseInt(request.getParameter("id"));
        Order existingOrder;
        try {

            existingOrder = orderDao.selectOrder(id);
            RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
            request.setAttribute("order", existingOrder);
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
