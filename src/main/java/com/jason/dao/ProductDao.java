package com.jason.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jason.model.Cart;
import com.jason.model.Product;

public class ProductDao {
    private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Awesome33@";
    private String jdbcDriver = "com.mysql.jdbc.Driver";


    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products" + " (image_location, name, description, price, stock) VALUES " + " (?,?,?,?,?);";
    private static final String SELECT_PRODUCT_BY_ID = "SELECT productId,image_location,name,description,price,stock FROM products WHERE productId=?";
    private static final String SELECT_ALL_PRODUCTS = "SELECT * FROM products";
    private static final String DELETE_PRODUCTS_SQL = "DELETE FROM products WHERE productId = ?;";
    private static final String UPDATE_PRODUCTS_SQL = "UPDATE products SET image_location=?,name=?, description=?, price=?,stock=? WHERE productId = ?;";
    private static final String SELECT_PRODUCT_BY_NAME = "SELECT * FROM products WHERE name = ?;";

    public ProductDao() {

    }

    protected Connection getConnection() {
        DBCon dbCon = new DBCon();
        return dbCon.getConnection();
    }

    //insert product
    public void insertProduct(Product product) {
        System.out.println(INSERT_PRODUCT_SQL);
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setNString(1, product.getImageLocation());
            preparedStatement.setNString(2, product.getName());
            preparedStatement.setNString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getStock());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }


    }

    //select product by id
    public Product selectProduct(int id) {
        Product product = null;
        //Establish connection
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            //Execute or update query
            ResultSet rs = preparedStatement.executeQuery();

            //Process result set object
            while (rs.next()) {
                String imageLocation = rs.getString("image_location");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = Double.parseDouble(rs.getString("price"));
                int stock = Integer.parseInt(rs.getString("stock"));
                product = new Product(id, imageLocation, name, description, price, stock);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;

    }

    //select all Products
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();

        //Establish connection
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);) {
            System.out.println(preparedStatement);
            //Execute or update query
            ResultSet rs = preparedStatement.executeQuery();

            //Process result set object
            while (rs.next()) {
                String imageLocation = rs.getString("image_location");
                int id = rs.getInt("productId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                products.add(new Product(id, imageLocation, name, description, price, stock));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }

    //update product
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        //Establish connection
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);) {
            System.out.println("Updated Product: " + preparedStatement);
            preparedStatement.setNString(1, product.getImageLocation());
            preparedStatement.setNString(2, product.getName());
            preparedStatement.setNString(3, product.getDescription());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setInt(5, product.getStock());
            preparedStatement.setInt(6, product.getId());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    //delete product by id
    public boolean deleteProduct(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCTS_SQL);) {
            System.out.println("Updated Product: " + preparedStatement);
            preparedStatement.setInt(1, id);

            rowDeleted = preparedStatement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    //Search product by name
    public Product searchName(String name) {
        Product product = null;
        try (Connection connection = getConnection();
             //Create statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_NAME);) {
            preparedStatement.setString(1, name);
            System.out.println(preparedStatement);
            //Execute or update query
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs);
            //Process result set object
            while (rs.next()) {
                int id = rs.getInt("productId");
                String imageLocation = rs.getString("image_location");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                System.out.println("Product Found: " + id + " " + name);
                product = new Product(id, imageLocation, name, description, price, stock);
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
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

    public double getTotalCartPrice(ArrayList<Cart> cartList) {
        double sum = 0;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    try (Connection connection = getConnection();
                         //Create statement using connection object
                         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
                        preparedStatement.setInt(1, item.getId());
                        System.out.println(preparedStatement);
                        ResultSet rs = preparedStatement.executeQuery();
                        while (rs.next()) {
                            sum += rs.getDouble("price") * item.getQuantity();
                        }

                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sum;
    }

    public List<Cart> getCartProducts(ArrayList<Cart> cartList) {
        List<Cart> cartItems = new ArrayList<>();
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    try (Connection connection = getConnection();
                         //Create statement using connection object
                         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
                        preparedStatement.setInt(1, item.getId());
                        System.out.println(preparedStatement);
                        ResultSet rs = preparedStatement.executeQuery();
                        while (rs.next()) {
                            Cart row = new Cart();
                            row.setId(rs.getInt("productId"));
                            row.setName(rs.getString("name"));
                            row.setPrice(rs.getDouble("price") * item.getQuantity());
                            row.setQuantity(item.getQuantity());
                            cartItems.add(row);

                        }

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return cartItems;
    }


}
