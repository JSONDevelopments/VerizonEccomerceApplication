package com.jason.controller.product;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jason.model.Product;
import com.jason.dao.ProductDao;

/**
 * Servlet implementation class ProductEditServlet
 */
@WebServlet("/product-edit")
public class ProductEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProductDao productDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductEditServlet() {
        super();
        productDao = new ProductDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ProductEditServlet doGet called.");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		int id = Integer.parseInt(request.getParameter("id"));
		Product existingProduct;
		try {
			existingProduct = productDao.selectProduct(id);
			RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
			request.setAttribute("product", existingProduct);
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
