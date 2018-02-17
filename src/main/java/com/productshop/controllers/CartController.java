package com.productshop.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import com.productshop.core.*;
import com.productshop.models.*;
import com.productshop.security.AuthenticationManager;
import com.productshop.services.*;

public class CartController extends BaseController {
	
	public void actionIndex() throws ControllerException {
		CartService servise = new CartService();
		ArrayList<ProductPosition> productPositions = servise.getProductPositions(getContext().getCookieManager());
		getContext().setAttribute("productPositions", productPositions);
	}
	
	public void actionAdd() throws ControllerException {
		Messenger messenger = getContext().getMessenger();
		
		if(getContext().isMethodPOST()) {
			String quantity = getContext().getRequestParameter("quantity");
			String itemCode = getContext().getRequestParameter("item-code");
			String price = getContext().getRequestParameter("price");
			
			if(quantity.isEmpty() || itemCode.isEmpty() || price.isEmpty()) {
				messenger.addErrorMessage("Ошибка. Товар не был добавлен. Некорректное количество товара.");
			}
			
			int itemQuantity = Integer.valueOf(quantity);
			int itemID = Integer.valueOf(itemCode);
			BigDecimal itemPrice = new BigDecimal(price);
			
			if(itemQuantity <= 0) {
				messenger.addErrorMessage("Ошибка. Товар не был добавлен. Количество не может быть нулевым.");
			}
			
			if(messenger.getErrorMessages().size() > 0) {
				return;
			}

			Item item;
			
			CatalogService catalogService = new CatalogService();
			try {
				item = catalogService.getItemByCode(String.valueOf(itemID));
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			ProductPosition position = new ProductPosition(item, itemQuantity, itemPrice);
			CartService cartServise = new CartService();
			cartServise.setProductPosition(position, getContext().getCookieManager());
			
			String url = getContext().getRequest().getContextPath() + "/cart/add";
			RedirectManager rm = getContext().getRedirectManager();
			try {
				rm.goTo(url);
				return;
			} catch (IOException e) {
				throw new ControllerException(e.getMessage(), e);
			}
		}
		
		messenger.addSuccessMessage("Товар был успешно добавлен в корзину.");
	}
	
	public void actionDelete(int id) throws ControllerException {
		CartService servise = new CartService();
		servise.deleteProductPosition(id, getContext().getCookieManager());
		
		String url = getContext().getRequest().getContextPath() + "/cart/deleted";
		RedirectManager rm = getContext().getRedirectManager();
		try {
			rm.goTo(url);
			return;
		} catch (IOException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	}
	
	public void actionDeleted() throws ControllerException {
		Messenger messenger = getContext().getMessenger();
		messenger.addSuccessMessage("Товар был удален из корзины.");
	}
	
	public void actionAddorder() throws ControllerException {
		if(getContext().isMethodPOST()) {
			AuthenticationManager am = getContext().getAuthenticationManager();
			
			if(!am.isAuthenticated()) {
				String url = getContext().getRequest().getContextPath() + "/account/login";
				RedirectManager rm = getContext().getRedirectManager();
				try {
					rm.goTo(url);
					return;
				} catch (IOException e) {
					throw new ControllerException(e.getMessage(), e);
				}
			}
			
			int userID = am.getUserID();
			
			OrderService orderServise = new OrderService();
			CartService cartServise = new CartService();
			UserService userService = new UserService();
			
			long result = 0;
			
			try {
				ArrayList<ProductPosition> productPositions = cartServise.getProductPositions(getContext().getCookieManager());
				User user = userService.getUserByID(userID);
				
				Order order = new Order(user);
				order.setProductPositions(productPositions);
				
				result = orderServise.createOrder(order, getContext().getCookieManager());
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			Messenger messenger = getContext().getMessenger();
			
			if(result > 0) {
				messenger.addSuccessMessage("Ваш заказ был оформлен. Наш менеджер свяжется с вами в ближайшее время.");
			} else {
				messenger.addErrorMessage("Ошибка. Заказ не был оформлен.");
			}
		}
	}

}
