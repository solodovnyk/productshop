package site.productshop.core;

import java.util.LinkedHashMap;
import javax.servlet.http.*;
import site.productshop.entities.ProductPosition;

public class CookieManager {
	private static final CookieManager cm = new CookieManager();
	private final ThreadLocal<Context> context = new ThreadLocal<>();
	private int cookieQuantity;
	
	private CookieManager() {}

	public static CookieManager get(Context context) {
		CookieManager.cm.context.set(context);
		return CookieManager.cm;
	}

	public int getCookieQuantity() {
		return cookieQuantity;
	}
	
	public void setCookie(String name, String value) {
		Context context = this.context.get();
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(60 * 60 * 24 * 14);
		cookie.setPath(context.getRequest().getContextPath() + "/");
		context.getResponse().addCookie(cookie);
	}
	
	public LinkedHashMap<String, String> getCookies() {
		Context context = this.context.get();

		LinkedHashMap<String, String> cookieValues = new LinkedHashMap<>();
		
		Cookie[] cookies = context.getRequest().getCookies();
		
		if(cookies == null) {
			return null;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("JSESSIONID")) {
				continue;
			}
			
			cookieValues.put(cookie.getName(), cookie.getValue());
		}
		
		return cookieValues;
	}

	public int getQuantity() throws Exception {
		Context context = this.context.get();

		int result = 0;
		Cookie[] cookies = context.getRequest().getCookies();
		
		if(cookies == null) {
			return 0;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("JSESSIONID")) {
				continue;
			}
			
			ProductPosition position = ProductPosition.toObject(cookie.getValue());
			result += position.getQuantity();
		}
		
		return result;
	}
	
	public void deleteCookie(String name) {
		Context context = this.context.get();

		Cookie[] cookies = context.getRequest().getCookies();

		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals(name)) {
					cookie.setPath(context.getRequest().getContextPath() + "/");
					cookie.setMaxAge(0);
					context.getResponse().addCookie(cookie);
					return;
				}
			}
		}
	}
	
	public void deleteAllCookies() {
		Context context = this.context.get();

		Cookie[] cookies = context.getRequest().getCookies();
		
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				
				if(cookie.getName().equals("JSESSIONID")) {
					continue;
				}
				
				cookie.setPath(context.getRequest().getContextPath() + "/");
				cookie.setMaxAge(0);
				context.getResponse().addCookie(cookie);
			}
		}
	}
}