package com.jason.controller.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;
import java.text.SimpleDateFormat;

import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.dao.*;
import com.jason.model.*;


@WebServlet("/order-now")
public class OrderNowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;
    ProductDao productDao;
    UserDao userDao;
    public OrderNowServlet() {

        orderDao = new OrderDao();
        productDao = new ProductDao();
        userDao = new UserDao();
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            User auth = (User) request.getSession().getAttribute("account");

            if (auth != null) {
                String productId = request.getParameter("id");
                System.out.println("Order Now pressed. Product id = " + productId);
                int productQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (productQuantity <= 0) {
                    productQuantity = 1;
                }
                Order orderModel = new Order();
                orderModel.setProductId(Integer.parseInt(productId));
                orderModel.setUserId(auth.getId());
                orderModel.setQuantity(productQuantity);
                orderModel.setDate(formatter.format(date));
                orderModel.setStatus("Order Recieved");

                boolean result = orderDao.insertOrder(orderModel);
                DecimalFormat dcf = new DecimalFormat("#.##");
                Product product = productDao.selectProduct(orderModel.getProductId());
                String emailMessage =
                        "<body style=\"font-family:Arial\"><basefont face=\"Arial\"><h2>Ecommerce Tech</h2> <p> Thank you for your purchase: </p> " +
                                "<table style = \"border: 1px solid black; border-collapse:collapse; width:100%\">" +
                                "<thead>\n" +
                                "        <tr style = \"border: 1px solid black; border-collapse:collapse; width:100%\">\n" +
                                "            <th scope=\"col\">Date</th>\n" +
                                "            <th scope=\"col\">Name</th>\n" +
                                "            <th scope=\"col\">Quantity</th>\n" +
                                "            <th scope=\"col\">Price</th>\n" +
                                "            <th scope=\"col\">Status</th>\n" +
                                "        </tr>\n" +
                                "        </thead>\n" +
                                "<tr style = \"border: 1px solid black; border-collapse:collapse; width:100%\"><td>" + orderModel.getDate()+"</td>" +
                                "<td>" +  product.getName() +"</td>" +
                                "<td>" + orderModel.getQuantity() + "</td>" +
                                "<td>"+ dcf.format((orderModel.getQuantity() * product.getPrice())) + "</td>" +
                                "<td>"+ orderModel.getStatus() + "</td>" +
                                "</tr>" +"</tbody></table></body>";
                try {
                    userDao.sendEmail(auth.getEmail(), "Ecommerce Order Summary", emailMessage);
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }

                if (result) {
                    ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                    if (cart_list != null) {
                        for (Cart c : cart_list) {
                            if (c.getId() == Integer.parseInt(productId)) {
                                cart_list.remove(cart_list.indexOf(c));
                                break;
                            }
                        }
                    }
                    System.out.println("Order Now pressed. Redirecting to User Orders Servlet...");
                    response.sendRedirect("user-orders");
                } else {
                    out.println("order failed");
                    request.setAttribute("notificationText", "Order failed to be placed.");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/customer-page");
                    dispatcher.forward(request, response);
                }
            } else {

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-login");
                dispatcher.forward(request, response);
            }

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}