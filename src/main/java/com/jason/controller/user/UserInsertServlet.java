package com.jason.controller.user;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.internet.AddressException;
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
 * Servlet implementation class UserInsertServlet
 */
@WebServlet("/user-insert")
public class UserInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserDao userDao;
	HttpSession session;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserInsertServlet() {
        super();
        userDao = new UserDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("UserInsertServlet doGet called.");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String encryptedPassword;
		try {
			encryptedPassword = userDao.generateStorngPasswordHash(request.getParameter("password"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
		String billingAddress = request.getParameter("billing_address");
		System.out.println("Attempting to insert: " + name + " " + email);
		System.out.println("Encrypted Pass: " + encryptedPassword);
		User newUser = new User(name, email, encryptedPassword, billingAddress);
		session = request.getSession();
		if (userDao.searchEmail(email) != null) {
			request.setAttribute("errorText", "Email is already associated with another account.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			dispatcher.forward(request, response);
		} else {
			if (request.getParameter("admin") == null || !request.getParameter("admin").equals("1")) {
				newUser.setAdmin(0);
			} else {
				newUser.setAdmin(1);
			}
			if (session.getAttribute("account") == null) {
				System.out.println("New User Logged in");
				session = request.getSession();
				session.setAttribute("account", newUser);
				try {
					userDao.sendEmail(newUser.getEmail(), "Ecommerce Sign Up", "<h1>Ecommerce Tech</h1> <br/> <p>Thank you for creating an account " +
							newUser.getName() + ". Continue shopping" +
							" <a href=\"http://localhost:8080/EcommerceApplication_war\">here</a></p>");
				} catch (AddressException e) {
					throw new RuntimeException(e);
				}
			}
			userDao.insertUser(newUser);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/user-homepage");
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
