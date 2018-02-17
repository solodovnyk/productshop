package com.productshop.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.productshop.models.User;
import com.productshop.services.ServiceException;
import com.productshop.services.UserService;

public class AuthenticationManager {

	public static final int USER = 1;
	public static final int ADMIN = 2;
	
	private HttpSession session;
	private Authentication authentication;
	
	public AuthenticationManager(HttpServletRequest request) {
		this.session = request.getSession();
		this.authentication = getAuthentication();
	}
	
	private Authentication setAuthentication(User user) {
		Authentication authentication = new Authentication(user);
		session.setAttribute("authentication", authentication);
		return authentication;
	}

	private Authentication getAuthentication() {
		Authentication authentication = (Authentication) session.getAttribute("authentication");
		return authentication;
	}
	
	public void destroyAuthentication() {
		session.invalidate();
	}
	
	public boolean isAuthenticated() {
		return authentication != null ? true : false;
	}
	
	public int getUserRoleID() throws SecurityException {
		
		if(authentication == null) {
			return 0;
		}
		
		int userRole = 0;
		UserService service = new UserService();
		
		try {
			userRole = service.getRoleByUserID(authentication.getUserID());
		} catch (ServiceException e) {
			throw new SecurityException(e.getMessage(), e);
		}
		
		return userRole;
	}
	
	public boolean hasAccess(int[] allowedRoles) throws SecurityException {
		
		boolean result = false;
		
		int userRole = getUserRoleID();
		
		for(int allowedRole : allowedRoles) {
			
			if(allowedRole == userRole) {
				return true;
			}
		}
		
		return result;
	}
	
	public void createNewAuthentication(User user) {
		this.authentication = setAuthentication(user);
	}
	
	public int getUserID() {
		
		Authentication auth = getAuthentication();
		
		if(auth == null) {
			return 0;
		}
		
		return getAuthentication().getUserID();
	}
	
}
