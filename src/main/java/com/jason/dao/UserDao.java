package com.jason.dao;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jason.model.User;

public class UserDao {

	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc?allowPublicKeyRetrieval=true&useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Awesome33@";
	private String jdbcDriver = "com.mysql.jdbc.Driver";

	private static final String INSERT_USERS_SQL = "INSERT INTO customers" + " (name,email,password,billing_address,admin) VALUES " + " (?,?,?,?,?);";
	private static final String INSERT_USERS_ADMIN = "INSERT INTO customers" + " (name,email,password,billing_address,admin) VALUES " + " (?,?,?,?,?);";
	private static final String SELECT_USER_BY_ID = "SELECT customerId,name,email,password,billing_address,admin FROM customers WHERE customerId=?";
	private static final String SELECT_ALL_USERS = "SELECT * FROM customers";
	private static final String DELETE_USERS_SQL = "DELETE FROM customers WHERE customerId = ?;";
	private static final String UPDATE_USERS_SQL = "UPDATE customers SET name = ?, email = ?, password = ?, billing_address = ?, admin = ? WHERE customerId = ?;";
	private static final String SELECT_USER_BY_EMAIL_PASSWORD = "SELECT * FROM customers WHERE email = ? AND password = ?;";
	private static final String SELECT_USER_BY_EMAIL = "SELECT * FROM customers WHERE email = ?;";

	public UserDao() {

	}

	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(jdbcDriver);
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	//insert user
	public void insertUser(User user) {
		System.out.println(INSERT_USERS_SQL);
		try (Connection connection = getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_ADMIN)) {
			preparedStatement.setNString(1, user.getName());
			preparedStatement.setNString(2, user.getEmail());
			preparedStatement.setNString(3, user.getPassword());
			preparedStatement.setNString(4, user.getBillingAddress());
			preparedStatement.setInt(5, user.getAdmin());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}


	}

	//select user by id
	public User selectUser(int id) {
		User user = null;
		//Establish connection
		try (Connection connection = getConnection();
			 //Create statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();

			//Process result set object
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String billingAddress = rs.getString("billing_address");
				int admin = rs.getInt("admin");
				user = new User(id, name, email, password, billingAddress, admin);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;

	}

	//select all users
	public List<User> selectAllUsers() {
		List<User> users = new ArrayList<>();

		//Establish connection
		try (Connection connection = getConnection();
			 //Create statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();

			//Process result set object
			while (rs.next()) {
				int id = rs.getInt("customerId");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String billingAddress = rs.getString("billing_address");
				int admin = rs.getInt("admin");
				users.add(new User(id, name, email, password, billingAddress, admin));
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}

	//update user
	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		//Establish connection
		try (Connection connection = getConnection();
			 //Create statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			System.out.println("Updated User: " + preparedStatement);
			preparedStatement.setNString(1, user.getName());
			preparedStatement.setNString(2, user.getEmail());
			preparedStatement.setNString(3, user.getPassword());
			preparedStatement.setNString(4, user.getBillingAddress());
			preparedStatement.setInt(5, user.getAdmin());
			preparedStatement.setInt(6, user.getId());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	//delete user by id
	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
			 //Create statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USERS_SQL);) {
			System.out.println("Updated User: " + preparedStatement);
			preparedStatement.setInt(1, id);

			rowDeleted = preparedStatement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	//Print SQL Error
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getLocalizedMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

	public User searchEmailPassword(String email, String password) {
		User user = null;
		try (Connection connection = getConnection();
			 //Create statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL_PASSWORD);) {
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println(rs);
			//Process result set object
			while (rs.next()) {
				int id = rs.getInt("customerId");
				String name = rs.getString("name");
				String billingAddress = rs.getString("billing_address");
				int admin = rs.getInt("admin");
				System.out.println("User Found: " + id + " " + name);
				user = new User(id, name, email, password, billingAddress, admin);
			}

		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public User searchEmail(String email) {
		User user = null;
		try (Connection connection = getConnection();
			 //Create statement using connection object
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_EMAIL);) {
			preparedStatement.setString(1, email);
			System.out.println(preparedStatement);
			//Execute or update query
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println(rs);
			//Process result set object
			while (rs.next()) {
				int id = rs.getInt("customerId");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String billingAddress = rs.getString("billing_address");
				int admin = rs.getInt("admin");
				System.out.println("User Found: " + id + " " + name);
				user = new User(id, name, email, password, billingAddress, admin);
			}

		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

	public void sendEmail(String toEmail,String subjectMessage, String inputMessage) throws AddressException {

		String to = toEmail;

		String from = "Ecommerce@hcl.com";
		final String username = "96ec95c8b5f650";//generated by Mailtrap
		final String password = "2c5cd756e3519b";//generated by Mailtrap

		String host = "smtp.mailtrap.io";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "2525");

		// Get the Session object.
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subjectMessage);
			// Put your HTML content using HTML markup
			message.setContent(inputMessage, "text/html");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static String generateStorngPasswordHash(String password)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		int iterations = 1000;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + ":" + toHex(salt) + ":" + toHex(hash);
	}

	private static byte[] getSalt() throws NoSuchAlgorithmException
	{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private static String toHex(byte[] array) throws NoSuchAlgorithmException
	{
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);

		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0)
		{
			return String.format("%0"  +paddingLength + "d", 0) + hex;
		}else{
			return hex;
		}
	}

	public static boolean validatePassword(String originalPassword, String storedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		String[] parts = storedPassword.split(":");
		int iterations = Integer.parseInt(parts[0]);

		byte[] salt = fromHex(parts[1]);
		byte[] hash = fromHex(parts[2]);

		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(),
				salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] testHash = skf.generateSecret(spec).getEncoded();

		int diff = hash.length ^ testHash.length;
		for(int i = 0; i < hash.length && i < testHash.length; i++)
		{
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
	}
	private static byte[] fromHex(String hex) throws NoSuchAlgorithmException
	{
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i < bytes.length ;i++)
		{
			bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

}
	
