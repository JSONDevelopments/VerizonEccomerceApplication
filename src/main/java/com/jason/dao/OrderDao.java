package com.jason.dao;

import com.jason.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {

    private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Awesome33@";
    private String jdbcDriver = "com.mysql.jdbc.Driver";


    private static final String INSERT_ORDER_SQL = "INSERT INTO orders" + " (userId, productId, quantity, order_date, status) VALUES " + " (?,?,?,?,?);";
    private static final String SELECT_ORDER_BY_ID = "SELECT orderId,userId,productId,quantity,order_date,status FROM orders WHERE orderId=?";
    private static final String SELECT_ALL_ORDERS = "SELECT * FROM orders";
    private static final String DELETE_ORDER_SQL = "DELETE FROM orders WHERE orderId = ?;";
    private static final String UPDATE_ORDER_SQL = "UPDATE orders SET status=? WHERE orderId = ?;";
    private static final String SELECT_ORDERS_BY_USERID = "SELECT * FROM orders WHERE userId = ?;";

    public OrderDao() {

    }

    protected Connection getConnection() {
        DBCon dbCon = new DBCon();
        return dbCon.getConnection();
    }

    //insert order
    public boolean insertOrder(Order order) {
        System.out.println(INSERT_ORDER_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getProductId());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setNString(4, order.getDate());
            preparedStatement.setNString(5, order.getStatus());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            return false;
        }
        return true;
    }

    //select order by id
    public Order selectOrder(int id) {
        Order order = null;
        //Establish connection
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            //Execute or update query
            ResultSet rs = preparedStatement.executeQuery();

            //Process result set object
            while (rs.next()) {
                int userId = Integer.parseInt(rs.getString("userId"));
                int productId = Integer.parseInt(rs.getString("productId"));
                int quantity = Integer.parseInt(rs.getString("quantity"));
                String orderDate = rs.getString("order_date");
                String status = rs.getString("status");
                order = new Order(id, userId, productId, quantity, orderDate, status);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return order;

    }

    //select all Orders
    public List<Order> selectAllOrders() {
        List<Order> orders = new ArrayList<>();

        //Establish connection
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ORDERS);) {
            System.out.println(preparedStatement);
            //Execute or update query
            ResultSet rs = preparedStatement.executeQuery();

            //Process result set object
            while (rs.next()) {
                int orderId = Integer.parseInt(rs.getString("orderId"));
                int userId = Integer.parseInt(rs.getString("userId"));
                int productId = Integer.parseInt(rs.getString("productId"));
                int quantity = Integer.parseInt(rs.getString("quantity"));
                String orderDate = rs.getString("order_date");
                String status = rs.getString("status");
                orders.add(new Order(orderId, userId, productId, quantity, orderDate, status));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return orders;
    }

    //update order
    public boolean updateOrder(int id, String status) throws SQLException {
        boolean rowUpdated;
        //Establish connection
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_SQL);) {
            System.out.println("Updated Order: " + preparedStatement);
            preparedStatement.setNString(1, status);
            preparedStatement.setInt(2, id);

            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        System.out.println("Order updated: " + rowUpdated);
        return rowUpdated;
    }

    //delete order by id
    public boolean deleteOrder(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_SQL);) {
            System.out.println("Updated Order: " + preparedStatement);
            preparedStatement.setInt(1, id);

            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    //Search order by user id
    public List<Order> selectOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<Order>();
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USERID)) {
            preparedStatement.setInt(1, userId);
            System.out.println(preparedStatement);
            //Execute or update query
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs);
            //Process result set object
            while (rs.next()) {
                System.out.println("Order Found, storing values.");
                int orderId = Integer.parseInt(rs.getString("orderId"));
                int productId = Integer.parseInt(rs.getString("productId"));
                int quantity = Integer.parseInt(rs.getString("quantity"));
                String orderDate = rs.getString("order_date");
                String status = rs.getString("status");
                System.out.println("Order Found: " + orderId + " " + userId + " " + productId);
                orders.add( new Order(orderId, userId, productId, quantity, orderDate, status));
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return orders;
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
}
