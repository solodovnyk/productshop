package com.productshop.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import com.productshop.core.*;
import com.productshop.models.*;
import com.productshop.security.*;
import com.productshop.security.SecurityException;
import com.productshop.services.*;

@ControllerConfig(defaultActionName = "admin-panel")
public class AdminController extends BaseController {

	@ActionConfig(defaultViewLayoutName = "admin-index")
	public void actionIndex() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		Messenger messenger = getContext().getMessenger();
		
		if(authManager.isAuthenticated()) {
			String url = getContext().getRequest().getContextPath() + "/admin/catalog";
			RedirectManager rm = getContext().getRedirectManager();
			
			try {
				rm.goTo(url);
				return;
			} catch (IOException e) {
				throw new ControllerException(e.getMessage(), e);
			}
		}

		if(getContext().isMethodPOST()) {
			String email = getContext().getRequestParameter("email");
			String password = getContext().getRequestParameter("password");
			
			if(email.isEmpty() || password.isEmpty()) {
				messenger.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			}
			
			if(messenger.getErrorMessages().size() > 0) {
				getContext().setAttribute("email", email);
			}
			
			boolean result = false;
			
			try {
				String hashPassword = Encryption.hash(password);
				UserService service = new UserService();
				result = service.authUser(email, hashPassword, authManager);
			} catch (ServiceException | SecurityException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result) {
				String url = getContext().getRequest().getContextPath() + "/admin/catalog";
				RedirectManager rm = getContext().getRedirectManager();
				try {
					rm.goTo(url);
					return;
				} catch (IOException e) {
					throw new ControllerException(e.getMessage(), e);
				}
			} else {
				messenger.addErrorMessage("Ошибка. Неверный логин или пароль.");
			}
		}
	}
	
	public void actionOut() throws ControllerException {
		AuthenticationManager am = getContext().getAuthenticationManager();
		am.destroyAuthentication();
		
		String url = getContext().getRequest().getContextPath() + "/admin/";
		RedirectManager rm = getContext().getRedirectManager();
		try {
			rm.goTo(url);
			return;
		} catch (IOException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	}
	
	public void actionCatalog() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}
		
		CatalogService service = new CatalogService();
		ArrayList<Category> categories = null;
		ArrayList<Item> items = null;
		
		try {
			categories = service.getAllCategories();
			items = service.getAllItems();
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("categories", categories);
		getContext().setAttribute("items", items);
		getContext().setAttribute("menuActiveItem", 1);
	}
	
	public void actionCategory(String categoryName) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}
		
		CatalogService service = new CatalogService();
		Category category = null;
		
		try {
			category = service.getCategoryBySlug(categoryName);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("category", category);
		getContext().setAttribute("menuActiveItem", 1);
	}
	
	public void actionAddcategory() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("menuActiveItem", 1);
		
		if(getContext().isMethodPOST()) {
			String name = getContext().getRequestParameter("name");
			String slug = getContext().getRequestParameter("slug");
			
			Messenger messenger = getContext().getMessenger();
			
			String pattern = "^([a-z0-9][a-z0-9\\-\\_]*[a-z0-9])|([a-z0-9]+)$";
			if(name.isEmpty() || slug.isEmpty() || !Pattern.compile(pattern).matcher(slug).find()) {
				messenger.addErrorMessage("Ошибка. Данные не корректны.");
			}
			
			if(messenger.getErrorMessages().size() > 0) {
				return;
			}
			
			Category category = new Category(name, slug);
			CatalogService service = new CatalogService();
			long result = 0;
			
			try {
				result = service.addCategory(category);
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				messenger.addSuccessMessage("Категория успешно добавлена.");
			} else {
				messenger.addErrorMessage("Ошибка. Категория не была добавлена.");
			}
		}
	}
	
	public void actionAddsubcategory(String categorySlug) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("menuActiveItem", 1);
		
		if(getContext().isMethodPOST()) {
			String name = getContext().getRequestParameter("name");
			String slug = getContext().getRequestParameter("slug");
			
			Messenger messages = getContext().getMessenger();
			
			String pattern = "^([a-z0-9][a-z0-9\\-\\_]*[a-z0-9])|([a-z0-9]+)$";
			if(name.isEmpty() || slug.isEmpty() || !Pattern.compile(pattern).matcher(slug).find()) {
				messages.addErrorMessage("Ошибка. Данные не корректны.");
			}
			
			if(messages.getErrorMessages().size() > 0) {
				getContext().setAttribute("categorySlug", categorySlug);
				return;
			}
			
			Category subCategory = new Category(name, slug);
			
			Part icon;
			long result = 0;
			
			try {
				icon = getContext().getRequest().getPart("icon");

				CatalogService service = new CatalogService();
			
				Category category = service.getCategoryBySlug(categorySlug);
				subCategory.setParent(category);
		
				result = service.addSubcategory(subCategory, icon, getContext().getRequest().getRealPath("/"));
			} catch (ServiceException | IOException | ServletException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				messages.addSuccessMessage("Подкатегория успешно добавлена.");
			} else {
				messages.addErrorMessage("Ошибка. Подкатегория не была добавлена.");
			}
			
		}
		
		String categoryName;
		
		
			CatalogService service = new CatalogService();
			
			try {
				categoryName = service.getCategoryNameBySlug(categorySlug);
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
		getContext().setAttribute("categoryName", categoryName);
		getContext().setAttribute("categorySlug", categorySlug);
	}
	
	public void actionAdditem() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 1);
		
		if(getContext().isMethodPOST()) {
			String name = getContext().getRequestParameter("name");
			String description = getContext().getRequestParameter("description");
			BigDecimal price = new BigDecimal(getContext().getRequestParameter("price"));
			
			Item item = new Item(name, description, price);
			item.setPhoto(getContext().getRequestParameter("photo"));
			
			String sale = getContext().getRequestParameter("sale");
			if(sale != null && !sale.isEmpty()) {
				item.setSale(new BigDecimal(Integer.valueOf(sale)));
			}
			
			String categorySlug = getContext().getRequestParameter("category");
			
			Part photo;
			CatalogService service = new CatalogService();
			long result = 0;
			
			try {
				photo = getContext().getRequest().getPart("photo");
				
				Category category = service.getSubcategoryBySlug(categorySlug);
				item.setCategory(category);
		
				result = service.addItem(item, photo, getContext().getRequest().getRealPath("/"));
			} catch (ServiceException | IOException | ServletException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			Messenger messages = getContext().getMessenger();
			
			if(result > 0) {
				messages.addSuccessMessage("Товар был успешно добавлен.");
			} else {
				messages.addErrorMessage("Ошибка. Товар не был добавлен.");
			}
		}
		
		CatalogService service = new CatalogService();
		ArrayList<Category> categories = null;
		
		try {
			categories = service.getAllCategories();
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("categories", categories);
	}
	
	public void actionEditcategory(String categorySlug) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 1);
		
		CatalogService service = new CatalogService();
		Category category = null;
		
		try {
			category = service.getCategoryBySlug(categorySlug);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		if(getContext().isMethodPOST()) {
			String oldSlug = category.getSlug();
			
			String name = getContext().getRequestParameter("name");
			String slug = getContext().getRequestParameter("slug");
			
			category.setName(name);
			category.setSlug(slug);
			
			int result = 0;
			
			try {
				result = service.editCategory(category, oldSlug);
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			Messenger messages = getContext().getMessenger();
			
			if(result > 0) {
				messages.addSuccessMessage("Категория была успешно изменена.");
			} else {
				messages.addErrorMessage("Ошибка. Категория не была изменена.");
			}
		}
		
		getContext().setAttribute("category", category);
	}
	
	public void actionEditsubcategory(String subcategorySlug) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 1);
		
		CatalogService service = new CatalogService();
		Category subcategory = null;
		
		try {
			subcategory = service.getCategoryBySlug(subcategorySlug);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		if(getContext().isMethodPOST()) {
			String oldSlug = subcategory.getSlug();
			
			String name = getContext().getRequestParameter("name");
			String slug = getContext().getRequestParameter("slug");
			
			if(subcategory.getIcon().indexOf("default.png") != -1) {
				subcategory.setIcon(null);
			}
			
			subcategory.setName(name);
			subcategory.setSlug(slug);
			
			Part icon = null;
			int result = 0;
			
			try {
				icon = getContext().getRequest().getPart("icon");
				result = service.editSubcategory(subcategory, oldSlug, icon, getContext().getRequest().getRealPath("/"));
			} catch (IOException | ServletException | ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			Messenger messages = getContext().getMessenger();
			
			if(result > 0) {
				messages.addSuccessMessage("Подкатегория была успешно изменена.");
			} else {
				messages.addErrorMessage("Ошибка. Подкатегория не была изменена.");
			}
		}
		
		getContext().setAttribute("subcategory", subcategory);
	}
	
	public void actionEdititem(String itemCode) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 1);
		
		CatalogService service = new CatalogService();
		Item item = null;
		
		try {
			item = service.getItemByCode(itemCode);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		if(getContext().isMethodPOST()) {
			String name = getContext().getRequestParameter("name");
			String price = getContext().getRequestParameter("price");
			String sale = getContext().getRequestParameter("sale");
			String description = getContext().getRequestParameter("description");
			
			if(item.getPhoto().indexOf("default.png") != -1) {
				item.setPhoto(null);
			}
			
			item.setName(name);
			item.setPrice(new BigDecimal(price));
			item.setSale(new BigDecimal(sale));
			item.setDescription(description);
			
			Part photo = null;
			int result = 0;
			
			try {
				photo = getContext().getRequest().getPart("photo");
				result = service.editItem(item, photo, getContext().getRequest().getRealPath("/"));
			} catch (IOException | ServletException | ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			Messenger messages = getContext().getMessenger();
			
			if(result > 0) {
				messages.addSuccessMessage("Товар был успешно изменен.");
			} else {
				messages.addErrorMessage("Ошибка. Товар не был изменен.");
			}
		}
		
		getContext().setAttribute("item", item);
	}
	
	public void actionDeletecategory(String categorySlug) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 1);
		
		CatalogService service = new CatalogService();
		int result;
		
		try {
			result = service.deleteCategoryBySlug(categorySlug);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		Messenger messages = getContext().getMessenger();
		
		if(result > 0) {
			messages.addSuccessMessage("Категория была успешно удалена.");
		} else {
			messages.addErrorMessage("Ошибка. Категория не была удалена.");
		}
	}

	public void actionDeleteitem(String itemCode) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 1);
		
		CatalogService service = new CatalogService();
		int result;
		
		try {
			result = service.deleteItemByCode(itemCode);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		Messenger messages = getContext().getMessenger();
		
		if(result > 0) {
			messages.addSuccessMessage("Товар был успешно удален.");
		} else {
			messages.addErrorMessage("Ошибка. Товар небыл удален.");
		}
	}
	
	public void actionOrders() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 3);
		
		OrderService service = new OrderService();
		ArrayList<Order> orders = null;
		
		try {
			orders = service.getAllOrders();
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	
		getContext().setAttribute("orders", orders);
	}
	
	public void actionChangestatus() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 3);
		
		if(getContext().isMethodPOST()) {
			int newStatus =  Integer.valueOf(getContext().getRequestParameter("status"));
			int orderID = Integer.valueOf(getContext().getRequestParameter("order-id"));
			
			Messenger messages = getContext().getMessenger();
			
			if(newStatus < 1 || newStatus > 3 || orderID < 1) {
				messages.addErrorMessage("Ошибка. Неправильный статус.");
				return;
			}
			
			OrderService service = new OrderService();
			
			int result;
			try {
				result = service.changeStatus(orderID, newStatus);
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				messages.addSuccessMessage("Статус заказа №" + orderID + " изменен.");
			}
		}
	}
	
	public void actionUsers() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 2);
		
		UserService service = new UserService();
		ArrayList<User> users = null;
		
		try {
			users = service.getAllUsers();
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	
		getContext().setAttribute("users", users);
	}
	
	public void actionMessages() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 4);
		
		MessageService service = new MessageService();
		ArrayList<Message> messages = null;
		
		try {
			messages = service.getAllMessages();
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	
		getContext().setAttribute("messages", messages);
	}
	
	public void actionUserinfo(int userID) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 2);
		
		UserService service = new UserService();
		User user = null;
		
		try {
			user = service.getUserByID(userID);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	
		getContext().setAttribute("user", user);
	}
	
	public void actionMessage(int messageID) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 4);
		
		MessageService service = new MessageService();
		Message message = null;
		
		try {
			message = service.getMessageByID(messageID);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	
		getContext().setAttribute("message", message);
	}
	
	public void actionEdituser(int userID) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 2);
		
		UserService service = new UserService();
		User user = null;
		
		try {
			user = service.getUserByID(userID);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		if(getContext().isMethodPOST()) {
			String name = getContext().getRequestParameter("name");
			String surname = getContext().getRequestParameter("surname");
			String phone = getContext().getRequestParameter("phone");
			
			Messenger messenger = getContext().getMessenger();
			
			if(name.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
				messenger.addErrorMessage("Ошибка. Все поля должны быть заполнены.");
			}
			
			if(messenger.getErrorMessages().size() > 0) {
				return;
			}
			
			user.setId(userID);
			user.setName(name);
			user.setSurname(surname);
			user.setPhone(phone);
			
			int result = 0;
			
			try {
				result = service.editUser(user);
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				messenger.addSuccessMessage("Данные пользователя были изменены.");
			} else {
				messenger.addErrorMessage("Ошибка. Данные пользователя не были изменены.");
			}
		}
		
		getContext().setAttribute("user", user);
	}
	
	public void actionDeleteuser(int userID) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 2);
		
		UserService service = new UserService();
		int result;
		
		try {
			result = service.deleteUser(userID);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		Messenger messages = getContext().getMessenger();
		
		if(result > 0) {
			messages.addSuccessMessage("Пользователь был удален.");
		} else {
			messages.addErrorMessage("Ошибка. Пользователь не был удален.");
		}
	}
	
	public void actionDeletemessage(int messageID) throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		
		try {
			if(!authManager.isAuthenticated() || !authManager.hasAccess(new int[]{2})) {
				getContext().getErrorManager().setErrorCode(403);
				return;
			}
		} catch (SecurityException e) {
			 throw new ControllerException(e.getMessage(), e);
		}		
		
		getContext().setAttribute("menuActiveItem", 4);
		
		MessageService service = new MessageService();
		int result;
		
		try {
			result = service.deleteMessage(messageID);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		Messenger messages = getContext().getMessenger();
		
		if(result > 0) {
			messages.addSuccessMessage("Сообщение было удалено.");
		} else {
			messages.addErrorMessage("Ошибка. Сообщение не было удалено.");
		}
	}
}