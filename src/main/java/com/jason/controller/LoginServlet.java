package com.jason.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jason.model.User;
import com.jason.dao.UserDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;
	HttpSession session;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		userDao = new UserDao();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("LoginServlet doGet called.");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		User user;
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("Entered password: " + password);
		if(userDao.searchEmail(email) != null) {
			System.out.println("Saved password: " + userDao.searchEmail(email).getPassword());
		}
		try {
			if (userDao.searchEmail(email) != null && userDao.validatePassword(password, userDao.searchEmail(email).getPassword())) {
				user = userDao.searchEmailPassword(email, userDao.searchEmail(email).getPassword());
				session = request.getSession();
				session.setAttribute("account", user);
				System.out.println("Logging in user: " + user.getName() + " " + user.getId() + " admin:" + user.getAdmin());
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-homepage");
				dispatcher.forward(request, response);

			} else {
				request.setAttribute("errorText", "No account with that email/password was found.");
				RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
				dispatcher.forward(request, response);
			}
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


}
