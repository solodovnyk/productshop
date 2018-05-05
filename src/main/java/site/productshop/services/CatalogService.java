package site.productshop.services;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.http.Part;
import site.productshop.config.Configurations;
import site.productshop.controllers.ControllerException;
import site.productshop.dao.CatalogDao;
import site.productshop.dao.DaoException;
import site.productshop.entities.Category;
import site.productshop.entities.Item;

public class CatalogService extends AbstractService {

	public Category getCategoryByID(int categoryID) throws ServiceException {
		
		Category category = null;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			category = dao.getCategoryByID(categoryID);
			
			prepareCategory(dao, category, true);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return category;
	}
	
	public Category getCategoryBySlug(String categorySlug) throws ServiceException {
		
		Category category = null;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			category = dao.getCategoryBySlug(categorySlug);
			
			prepareCategory(dao, category, true);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return category;
	}
	
	public ArrayList<Item> getItemsBySubcategory(Category subcategory, int sortMode, int pageNum) throws ServiceException {
		
		ArrayList<Item> items = new ArrayList<>();
		
		try {
			int limit = Integer.valueOf(Configurations.getComponentValue("settings", "itemQuantity"));
			int offset = pageNum * limit - limit;

			CatalogDao dao = getDao(CatalogDao.class);
			items = dao.getItemsBySubcategoryID(subcategory.getId(), sortMode, limit, offset);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		for(Item item : items) {
			prepareItem(item);
		}
		
		return items;
	}
	
	public ArrayList<Item> findItemsByKeyword(String keyword, int sortMode, int pageNum) throws ServiceException {
		
		ArrayList<Item> items = new ArrayList<>();
		
		try {
			int limit = Integer.valueOf(Configurations.getComponentValue("settings", "itemQuantity"));
			int offset = pageNum * limit - limit;

			CatalogDao dao = getDao(CatalogDao.class);
			items = dao.getItemsByKeyword(keyword, sortMode, limit, offset);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		for(Item item : items) {
			prepareItem(item);
		}
		
		return items;
	}
	
	public Category getSubcategoryBySlug(String subCategorySlug) throws ServiceException {
		
		Category subCategory = null;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			subCategory = dao.getCategoryBySlug(subCategorySlug);
			
			prepareCategory(dao, subCategory, true);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return subCategory;
	}
	
	public ArrayList<Category> getAllCategories() throws ServiceException {
		
		ArrayList<Category> categoryList = new ArrayList<>();
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			categoryList = dao.getAllCategories();
			
			for(Category category : categoryList) {
				prepareCategory(dao, category, true);
			}
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return categoryList;
	}
	
	public ArrayList<Item> getAllItems() throws ServiceException {
		
		ArrayList<Item> itemList = new ArrayList<>();
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			itemList = dao.getAllItems();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		for(Item item : itemList) {
			prepareItem(item);
		}
		
		return itemList;
	}
	
	public ArrayList<Item> getLastItems(int quantity) throws ServiceException {
	
		ArrayList<Item> itemList = new ArrayList<>();
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			itemList = dao.getLastItems(quantity);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		for(Item item : itemList) {
			prepareItem(item);
		}
		
		return itemList;
	}
	
	public ArrayList<Item> getLastDiscountItems(int quantity) throws ServiceException {
		
		ArrayList<Item> itemList = new ArrayList<>();
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			itemList = dao.getLastDiscountItems(quantity);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		for(Item item : itemList) {
			prepareItem(item);
		}
		
		return itemList;
	}
	
	public Item getItemByCode(String itemCode) throws ServiceException {
		
		Item item = null;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			item = dao.getItemByID(Integer.valueOf(itemCode));
			
			prepareItem(item);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return item;
		
	}
	
	public long addCategory(Category category) throws ServiceException {
		
		long resultID = 0;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			resultID = dao.addCategory(category);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return resultID;
	}
	
	public long addSubcategory(Category subcategory, Part icon, String contextPath) 
			throws ServiceException, ControllerException {
		
		long DB_ID = 0;
		String iconName = "";
		
		if(icon.getSize() > 0) {
			iconName = Integer.toString((int)((Math.random() * 1000000)));
			String iconFileType = 
    				icon.getSubmittedFileName()
    				.substring(icon.getSubmittedFileName().length()-3);
			iconName += "." + iconFileType;
			
			subcategory.setIcon(iconName);
		}
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			DB_ID = dao.addCategory(subcategory);
		
			if(DB_ID > 0 && icon.getSize() > 0 && iconName.length() > 0) {
	    		
	    		String iconFilePath = 
	        			contextPath + "resources/images/content/" + iconName;
	        	
	    		icon.write(iconFilePath);
			}
		} catch (IOException | DaoException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		return DB_ID;
	}
	
	public String getCategoryNameBySlug(String categorySlug) throws ServiceException {
		return getCategoryBySlug(categorySlug).getName();
	}
	
	public long addItem(Item item, Part photo, String contextPath) throws ServiceException {
		
		long DB_ID = 0;
		String photoName = "";
		
		if(photo.getSize() > 0) {
			photoName = Integer.toString((int)((Math.random() * 1000000)));
			String photoFileType = 
    				photo.getSubmittedFileName()
    				.substring(photo.getSubmittedFileName().length()-3);
			photoName += "." + photoFileType;
			
			item.setPhoto(photoName);
		}
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			DB_ID = dao.addItem(item);
			
			if(DB_ID > 0 && photo.getSize() > 0 && photoName.length() > 0) {
	    		
	    		String photoFilePath = 
	        			contextPath + "resources/images/content/" + photoName;
	        	
	    		photo.write(photoFilePath);
			}
			
		} catch (IOException | DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return DB_ID;
	}
	
	public int editCategory(Category category, String oldSlug) throws ServiceException {
		
		int result = 0;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.editCategory(category, oldSlug);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int editSubcategory(Category subcategory, String oldSlug, Part icon, String contextPath) 
			throws ServiceException, ControllerException {
		
		int result = 0;
		String iconName = subcategory.getIcon();
		
		if(icon != null && icon.getSize() > 0 && iconName == null) {
			iconName = Integer.toString((int)((Math.random() * 1000000)));
			String iconFileType = 
    				icon.getSubmittedFileName()
    				.substring(icon.getSubmittedFileName().length()-3);
			iconName += "." + iconFileType;
			
			subcategory.setIcon(iconName);
		}
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.editSubcategory(subcategory, oldSlug);
		
			if(result > 0 && icon != null && icon.getSize() > 0 && iconName.length() > 0) {
	    		
	    		String iconFilePath = 
	        			contextPath + "resources/images/content/" + iconName;
	        	
	    		icon.write(iconFilePath);
			}
		} catch (IOException | DaoException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int editItem(Item item, Part photo, String contextPath) 
			throws ServiceException, ControllerException {
		
		int result = 0;
		String photoName = item.getPhoto();
		
		if(photo != null && photo.getSize() > 0 && photoName == null) {
			photoName = Integer.toString((int)((Math.random() * 1000000)));
			String iconFileType = 
    				photo.getSubmittedFileName()
    				.substring(photo.getSubmittedFileName().length()-3);
			photoName += "." + iconFileType;
			
			item.setPhoto(photoName);
		}
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.editItem(item);
		
			if(result > 0 && photo != null && photo.getSize() > 0 && photoName.length() > 0) {
	    		
	    		String iconFilePath = 
	        			contextPath + "resources/images/content/" + photoName;
	        	
	    		photo.write(iconFilePath);
			}
		} catch (IOException | DaoException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int deleteCategoryBySlug(String categorySlug) throws ServiceException {
		
		int result = 0;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.deleteCategoryBySlug(categorySlug);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int deleteItemByCode(String itemCode) throws ServiceException {
		
		int result = 0;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.deleteItemByID(Integer.valueOf(itemCode));
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int getItemsQuantityBySubcategory(Category subcategory) throws ServiceException {
		
		int result = 0;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.getItemQuantityByCategory(subcategory);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int getItemsQuantityInSearchResults(String keyword) throws ServiceException {
		
		int result = 0;
		
		try {
			CatalogDao dao = getDao(CatalogDao.class);
			result = dao.getItemQuantityByKeyword(keyword);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	private Category prepareCategory(CatalogDao dao, Category category, boolean setParent) 
			throws DaoException {
		
		if(category == null) {
			return null;
		}
		
		Category parent = category.getParent();
		
		if(setParent && parent != null) {
			category.setParent(prepareCategory(dao, parent, true));
		}
		
		ArrayList<Category> subcategories = dao.getCategoriesByParentCategory(category);
		
		for(Category subcategory : subcategories) {
			subcategory.setParent(category);
			prepareCategory(dao, subcategory, false);
		}
		
		category.setSubcategories(subcategories);
		
		int categoryItems = dao.getItemQuantityByCategory(category);
		
		if(!subcategories.isEmpty()) {
			for(Category subcategory : subcategories) {
				categoryItems += dao.getItemQuantityByCategory(subcategory);
			}
		}
		
		category.setItemQuantity(categoryItems);
		
		if(category.getIcon() == null) {
			category.setIcon("default.png");
		}
		
		return category;
	}
	
	private Item prepareItem(Item item) {
		
		if(item == null) {
			return null;
		}
		
		BigDecimal sale = item.getSale();
		BigDecimal finalPrice = item.getFinalPrice();
		
		if(sale != null && finalPrice == null) {
			BigDecimal price = item.getPrice();
			BigDecimal discountAmount = price.divide(new BigDecimal(100)).multiply(sale);
			item.setFinalPrice(price.subtract(discountAmount));
		}
		
		if(item.getPhoto() == null) {
			item.setPhoto("default.png");
		}
		
		return item;
	}
}
