package com.productshop.models;

import java.math.BigDecimal;

import com.productshop.controllers.ControllerException;
import com.productshop.services.CatalogService;
import com.productshop.services.ServiceException;

public class ProductPosition {

	private int id;
	private int orderID;
	private Item item;
	private int quantity;
	private BigDecimal price;
	
	public ProductPosition(Item item, int quantity, BigDecimal price) {
		this.item = item;
		this.quantity = quantity;
		this.price = setScale(price);
	}
	
	public String toString() {
		return String.valueOf(item.getId()) + ":" + String.valueOf(quantity) + ":" + price.toString();
	}
	
	public static ProductPosition toObject(String position) throws ControllerException {
		String[] values = position.split(":");
		
		if(values.length != 3) {
			return null;
		}
		
		Item item = null;
		
		CatalogService catalogService = new CatalogService();
		try {
			item = catalogService.getItemByCode(String.valueOf(Integer.valueOf(values[0])));
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		int quantity = Integer.valueOf(values[1]);
		BigDecimal price = new BigDecimal(values[2]);
		
		return new ProductPosition(item, quantity, price);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	private BigDecimal setScale(BigDecimal decimal) {
		return decimal.setScale(2, BigDecimal.ROUND_CEILING);
	}

}
