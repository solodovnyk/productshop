package com.productshop.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Order {

	private int id;
	private User user;
	private OrderStatus orderStatus;
	private Date creatingDate;
	private ArrayList<ProductPosition> productPositions = new ArrayList<>();

	public Order(User user) {
		this.user = user;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getCreatingDate() {
		return creatingDate;
	}

	public void setCreatingDate(Date creatingDate) {
		this.creatingDate = creatingDate;
	}

	public ArrayList<ProductPosition> getProductPositions() {
		return productPositions;
	}

	public void setProductPositions(ArrayList<ProductPosition> productPositions) {
		this.productPositions = productPositions;
	}
	
	public BigDecimal getTotalPrice() {
		if(this.productPositions.size() == 0) {
			return null;
		}
		
		BigDecimal totalPrice = new BigDecimal(0);
		
		for(ProductPosition position : this.productPositions) {
			totalPrice = totalPrice.add(position.getPrice());
		}
		
		return totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

}
