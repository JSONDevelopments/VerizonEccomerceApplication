package com.jason.controller.order;

import com.jason.dao.OrderDao;
import com.jason.dao.ProductDao;
import com.jason.dao.UserDao;
import com.jason.model.Cart;
import com.jason.model.Order;
import com.jason.model.Product;
import com.jason.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/cart-check-out")
public class CartCheckOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    OrderDao orderDao;
    UserDao userDao;
    ProductDao productDao;
    public CartCheckOutServlet() {
        orderDao = new OrderDao();
        userDao = new UserDao();
        productDao = new ProductDao();
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
            Date date = new Date();
            DecimalFormat dcf = new DecimalFormat("#.##");
            User auth = (User) request.getSession().getAttribute("account");
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
                    "        </thead>\n";
            if(cart_list != null && auth!=null) {
                for(Cart c:cart_list) {
                    Order order = new Order();
                    order.setUserId(auth.getId());
                    order.setProductId(c.getId());
                    order.setQuantity(c.getQuantity());
                    order.setDate(formatter.format(date));
                    order.setStatus("Order Recieved");
                    //Setting email body
                    Product product = productDao.selectProduct(order.getProductId());
                    emailMessage+= "<tr style = \"border: 1px solid black; border-collapse:collapse; width:100%\"><td>" + order.getDate()+"</td>" +
                            "<td>" +  product.getName() +"</td>" +
                            "<td>" + order.getQuantity() + "</td>" +
                            "<td>"+ dcf.format((order.getQuantity() * product.getPrice())) + "</td>" +
                            "<td>"+ order.getStatus() + "</td>" +
                            "</tr>";

                    boolean result = orderDao.insertOrder(order);
                    if(!result) break;
                }
                emailMessage+="</tbody></table></body>";
                try {
                    userDao.sendEmail(auth.getEmail(), "Ecommerce Order Summary", emailMessage);
                } catch (AddressException e) {
                    throw new RuntimeException(e);
                }
                cart_list.clear();
                response.sendRedirect("user-orders");
            }else {
                if(auth==null) {
                    response.sendRedirect("user-login");
                }
                response.sendRedirect("cart.jsp");
            }
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}