package com.jason.controller.cart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jason.model.*;


@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        ArrayList<Cart> cartList = new ArrayList<>();
        int id = Integer.parseInt(request.getParameter("id"));
        Cart cm = new Cart();
        cm.setId(id);
        cm.setQuantity(1);
        HttpSession session = request.getSession();
        ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
        if (cart_list == null) {
            cartList.add(cm);
            session.setAttribute("cart-list", cartList);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/customer-page");
            dispatcher.forward(request, response);
        } else {
            cartList = cart_list;

            boolean exist = false;
            for (Cart c : cart_list) {
                if (c.getId() == id) {
                    exist = true;
                    int num = c.getQuantity();
                    c.setQuantity(num+1);
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/customer-page");
                    dispatcher.forward(request, response);
                }
            }

            if (!exist) {
                cartList.add(cm);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/customer-page");
                dispatcher.forward(request, response);
            }
        }
    }
}

