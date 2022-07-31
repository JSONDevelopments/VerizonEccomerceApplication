package com.jason.controller.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jason.model.User;

/**
 * Servlet implementation class SendUserHomeServlet
 */
@WebServlet("/user-homepage")
public class UserHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHomeServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SendUserHomeServlet called.");
		session = request.getSession();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		User user = (User) session.getAttribute("account");
		System.out.println("Session Admin: " + user.getAdmin());
		if(user.getAdmin() == 1) {
			response.sendRedirect("user-list");
		}
		else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/customer-page");
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
