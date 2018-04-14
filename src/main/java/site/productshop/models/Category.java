package com.productshop.models;

import java.util.ArrayList;
import java.util.Date;

public class Category {

	private int id;
	private String name;
	private Category parent;
	private String icon;
	private String slug;
	private Date creatingDate;
	private int itemQuantity;
	private ArrayList<Category> subcategories;
	
	public Category(String name, String slug) {
		this.name = name;
		this.setSlug(slug);
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
	
	public Category getParent() {
		return parent;
	}
	
	public void setParent(Category parent) {
		this.parent = parent;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Date getCreatingDate() {
		return creatingDate;
	}
	
	public void setCreatingDate(Date creatingDate) {
		this.creatingDate = creatingDate;
	}

	public ArrayList<Category> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(ArrayList<Category> subcategories) {
		this.subcategories = subcategories;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public int getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
}
