package com.jason.controller.order;

import com.jason.dao.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.nio.file.Paths;
import java.util.ArrayList;


import com.jason.model.Cart;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@WebServlet("/order-payment")
public class OrderPaymentServlet extends HttpServlet {
    OrderDao orderDao;
    UserDao userDao;
    ProductDao productDao;
    public OrderPaymentServlet() {
        orderDao = new OrderDao();
        userDao = new UserDao();
        productDao = new ProductDao();
        Stripe.apiKey = "sk_test_4eC39HqLyjWDarjtT1zdp7dc";
        }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String YOUR_DOMAIN = "http://localhost:8080";
        HttpSession session = request.getSession();
        ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN + "/cart-check-out")
                        .setCancelUrl(YOUR_DOMAIN + "/customer-page")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        // Provide the exact Price ID (for example, pr_1234) of the product you want to sell
                                        .setPrice(Double.toString(productDao.getTotalCartPrice(cart_list)))
                                        .build())
                        .build();

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
