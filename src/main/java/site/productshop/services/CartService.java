package site.productshop.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import site.productshop.controllers.ControllerException;
import site.productshop.core.CookieManager;
import site.productshop.entities.ProductPosition;

public class CartService {

	public void setProductPosition(ProductPosition position, CookieManager cm) throws ControllerException {
		
		LinkedHashMap<String, String> positions = cm.getCookies();
		
		for(Map.Entry<String, String> existingPosition : positions.entrySet()) {
			
			if(existingPosition.getKey().equals(String.valueOf(position.getItem().getId()))) {
				
				ProductPosition productPosition = ProductPosition.toObject(existingPosition.getValue());
				
				int startQuantity = productPosition.getQuantity();
				productPosition.setQuantity(startQuantity + position.getQuantity());
				
				position = productPosition;
				break;
			}
		}
		
		cm.setCookie(String.valueOf(position.getItem().getId()), position.toString());
	}
	
	public ArrayList<ProductPosition> getProductPositions(CookieManager cm) throws ControllerException {
		
		ArrayList<ProductPosition> positions = new ArrayList<>();
		
		LinkedHashMap<String, String> positionCookies = cm.getCookies();
		
		for(Map.Entry<String, String> existingPosition : positionCookies.entrySet()) {
			
			ProductPosition productPosition = ProductPosition.toObject(existingPosition.getValue());
			
			positions.add(productPosition);
		}
		
		return positions;
	}
	
	public int getItemsQuantityInCart(CookieManager cm) {
		return cm.getCookieQuantity();
	}

	public void deleteProductPosition(int id, CookieManager cm) throws ControllerException {
		cm.deleteCookie(String.valueOf(id));
	}
}
