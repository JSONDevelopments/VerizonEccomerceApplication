package com.jason.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int orderId;
    private int userId;
    private int productId;

    private int quantity;
    private String date;
    private String status;

    public Order(int userId, int productId, int quantity,String date, String status) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }
}
