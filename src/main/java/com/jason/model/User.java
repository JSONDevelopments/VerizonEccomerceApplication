package com.jason.model;

import lombok.Data;

@Data
public class User {
	private int id;
	private String name;
	private String email;
	private String password;
	private String billingAddress;
	private int admin;
	
	public User(int id, String name, String email, String password, String billingAddress, int admin) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
		this.admin = admin;
	}

	public User(int id, String name, String email, String password, String billingAddress) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
	}

	public User(String name, String email, String password, String billingAddress) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
	}
	public User(String name, String email, String password, String billingAddress, int admin) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.billingAddress = billingAddress;
		this.admin = admin;
	}



}
