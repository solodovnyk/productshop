package com.productshop.security;

import javax.servlet.http.HttpSession;

import com.productshop.models.User;
import com.productshop.services.ServiceException;
import com.productshop.services.UserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Component
@Scope(SCOPE_SESSION)
public class AuthenticationManager {
	
	private HttpSession session;
	private Authentication authentication;
	
	public AuthenticationManager() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			this.session = attributes.getRequest().getSession();
		} else {
			throw new IllegalStateException("No session");
		}
		this.authentication = getAuthentication();
	}
	
	private Authentication initAuthentication(User user) {
		Authentication authentication = new Authentication(user);
		session.setAttribute("authentication", authentication);
		return authentication;
	}

	private Authentication getAuthentication() {
		return (Authentication) session.getAttribute("authentication");
	}
	
	public void destroyAuthentication() {
		session.invalidate();
	}
	
	public boolean isAuthenticated() {
		return authentication != null;
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
		this.authentication = initAuthentication(user);
	}
	
	public int getUserID() {
		
		Authentication auth = getAuthentication();
		
		if(auth == null) {
			return 0;
		}
		
		return getAuthentication().getUserID();
	}
	
}
