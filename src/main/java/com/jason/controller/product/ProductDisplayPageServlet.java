package com.jason.controller.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jason.model.Product;
import com.jason.dao.ProductDao;

/**
 * Servlet implementation class ProductDisplayPageServlet
 */
@WebServlet("/customer-page")
public class ProductDisplayPageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ProductDao productDao;

    public ProductDisplayPageServlet() {
        productDao = new ProductDao();
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ProductDisplayPageServlet doGet called.");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        try {
            List<Product> listProduct = productDao.selectAllProducts();
            request.setAttribute("products", listProduct);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer-page.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
