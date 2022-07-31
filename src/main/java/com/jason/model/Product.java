package com.jason.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class Product {
	private int id;
	private String imageLocation;
	private String name;
	private String description;
	private double price;
	private int stock;
	public Product() {

	}

	public Product(String imageLocation, String name, String description, double price, int stock) {
		this.imageLocation = imageLocation;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}

	public Product(int id, String imageLocation, String name, String description, double price, int stock) {
		this.id = id;
		this.imageLocation = imageLocation;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}


}
