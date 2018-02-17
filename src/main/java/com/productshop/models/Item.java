package com.productshop.models;

import java.math.BigDecimal;
import java.util.Date;

public class Item {

	private int id;
	private String name;
	private Category category;
	private String description;
	private String photo;
	private BigDecimal price;
	private BigDecimal sale;
	private BigDecimal finalPrice;
	private Date additionDate;

	public Item(String name, String description, BigDecimal price) {
		this.name = name;
		this.description = description;
		this.price = setScale(price);
	}
	
	public String getPriceFraction() {
		String price = this.price.toString();
		return price.substring(price.length()-2);
	}
	
	public String getFinalPriceFraction() {
		String price = this.finalPrice.toString();
		return price.substring(price.length()-2);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = setScale(price);
	}

	public BigDecimal getSale() {
		return sale;
	}

	public void setSale(BigDecimal sale) {
		this.sale = setScale(sale);
	}

	public Date getAdditionDate() {
		return additionDate;
	}

	public void setAdditionDate(Date additionDate) {
		this.additionDate = additionDate;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = setScale(finalPrice);
	}
	
	public String getCode() {
		
		String code = Integer.toString(this.id);
		int codeLength = code.length();
		
		if(codeLength < 5) {
			
			int diff = 5 - codeLength;
			
			for(int i = 1; i <= diff; i++) {
				code = "0" + code;
			}
		}
		
		return code;
	}
	
	private BigDecimal setScale(BigDecimal decimal) {
		return decimal.setScale(2, BigDecimal.ROUND_CEILING);
	}
}
