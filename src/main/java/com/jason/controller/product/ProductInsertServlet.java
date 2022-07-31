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
 * Servlet implementation class ProductInsertServlet
 */
@WebServlet("/product-insert")
public class ProductInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ProductDao productDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductInsertServlet() {
        super();
        productDao = new ProductDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ProductInsertServlet doGet called.");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String imageLocation = request.getParameter("imageLocation");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		double price = Double.parseDouble(request.getParameter("price"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		System.out.println("Attempting to insert: " + name + " " + price);
		Product newProduct = new Product(imageLocation,name, description, price, stock);
		System.out.println("Image Location: " + imageLocation);
		if(productDao.searchName(name) != null) {
			request.setAttribute("errorText", "Product Name is already used for another product.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("product-form.jsp");
			dispatcher.forward(request, response);
		}
		else {
			productDao.insertProduct(newProduct);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/product-list");
			dispatcher.forward(request, response);
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
