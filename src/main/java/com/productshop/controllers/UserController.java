package com.productshop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import com.productshop.core.Messenger;
import com.productshop.core.RedirectManager;
import com.productshop.models.Order;
import com.productshop.models.User;
import com.productshop.security.AuthenticationManager;
import com.productshop.security.Encryption;
import com.productshop.security.SecurityException;
import com.productshop.services.OrderService;
import com.productshop.services.ServiceException;
import com.productshop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class UserController extends BaseController {
	
	public void actionIndex() throws ControllerException {
		AuthenticationManager am = getContext().getAuthenticationManager();
		
		User user = null;
		ArrayList<Order> orders = null;
		
		try {
			UserService userService = new UserService();
			OrderService orderService = new OrderService();
			
			user = userService.getUserByID(am.getUserID());
			getContext().setAttribute("user", user);
			
			if(user == null) {
				String url = getContext().getRequest().getContextPath() + "/account/login";
				RedirectManager rm = getContext().getRedirectManager();
				try {
					rm.goTo(url);
					return;
				} catch (IOException e) {
					throw new ControllerException(e.getMessage(), e);
				}
			}
			
			orders = orderService.getOrdersByUserID(user.getId());
			getContext().setAttribute("orders", orders);
			
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	}

	@GetMapping("/login")
	public String renderLoginPage(Model model) {
		model.addAttribute("page", "user/login");
		return MAIN_LAYOUT_PATH;
	}

	public void actionLogin() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		Messenger messages = getContext().getMessenger();
		
		if(authManager.isAuthenticated()) {
			String url = getContext().getRequest().getContextPath() + "/account";
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
				messages.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			}
			
			if(messages.getErrorMessages().size() > 0) {
				getContext().setAttribute("email", email);
				return;
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
				String url = getContext().getRequest().getContextPath() + "/account";
				RedirectManager rm = getContext().getRedirectManager();
				try {
					rm.goTo(url);
					return;
				} catch (IOException e) {
					throw new ControllerException(e.getMessage(), e);
				}
			} else {
				messages.addErrorMessage("Ошибка. Неверный логин или пароль.");
			}
		}
	}

	@GetMapping("/registration")
	public String renderRegistrationPage(Model model) {
		model.addAttribute("page", "user/registration");
		return MAIN_LAYOUT_PATH;
	}
	
	public void actionRegistration() throws ControllerException {
		AuthenticationManager authManager = getContext().getAuthenticationManager();
		Messenger messages = getContext().getMessenger();
		
		if(authManager.isAuthenticated()) {
			String url = getContext().getRequest().getContextPath() + "/account";
			RedirectManager rm = getContext().getRedirectManager();
			
			try {
				rm.goTo(url);
				return;
			} catch (IOException e) {
				throw new ControllerException(e.getMessage(), e);
			}
		}
		
		if(getContext().isMethodPOST()) {
			String name = getContext().getRequestParameter("name");
			String surname = getContext().getRequestParameter("surname");
			String email = getContext().getRequestParameter("email");
			String phone = getContext().getRequestParameter("phone");
			String password = getContext().getRequestParameter("password");
			String  confirmedPassword = getContext().getRequestParameter("confirm-password");
			
			User user = new User(name, surname, email, phone, password);
			
			if(!password.equals(confirmedPassword)) {
				messages.addErrorMessage("Ошибка. Пароли не совпадают.");
			}
			
			if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty() ||
			   password.isEmpty() || confirmedPassword.isEmpty()) {
				
				messages.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			}
			
			if(messages.getErrorMessages().size() > 0) {
				getContext().setAttribute("user", user);
				return;
			}
			
			long result = 0;
			
			try {
				String hashPassword = Encryption.hash(password);
				user.setPassword(hashPassword);
				
				UserService service = new UserService();
				
				result = service.createNewUser(user, authManager);
			} catch (ServiceException | SecurityException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				String url = getContext().getRequest().getContextPath() + "/account";
				RedirectManager rm = getContext().getRedirectManager();
				try {
					rm.goTo(url);
					return;
				} catch (IOException e) {
					throw new ControllerException(e.getMessage(), e);
				}
			} else {
				messages.addErrorMessage("Ошибка. Регистрация не была завершена.");
			}
			
			getContext().setAttribute("user", user);
		}
	}
	
	public void actionOut() throws ControllerException {
		AuthenticationManager am = getContext().getAuthenticationManager();
		am.destroyAuthentication();
		
		String url = getContext().getRequest().getContextPath();
		RedirectManager rm = getContext().getRedirectManager();
		try {
			rm.goTo(url);
			return;
		} catch (IOException e) {
			throw new ControllerException(e.getMessage(), e);
		}
	}
	
	public void actionEdituser() throws ControllerException {
		if(getContext().isMethodPOST()) {
			AuthenticationManager am = getContext().getAuthenticationManager();
			boolean passwordChange = false;
			
			String name = getContext().getRequestParameter("name");
			String surname = getContext().getRequestParameter("surname");
			String phone = getContext().getRequestParameter("phone");
			
			Messenger messages = getContext().getMessenger();
			
			if(name.isEmpty() || surname.isEmpty() || phone.isEmpty()) {
				messages.addErrorMessage("Ошибка. Поля \"имя\", \"Фамилия\", и \"телефон\" должны быть заполнены.");
			}
			
			String newPassword = getContext().getRequestParameter("new-password");
			String confirmNewPassword = getContext().getRequestParameter("confirm-new-password");
			
			if(!newPassword.isEmpty() || !confirmNewPassword.isEmpty()) {
				passwordChange = true;
				
				if(newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
					messages.addErrorMessage("Ошибка. При изменении пароля поля \"новый пароль\", и \"повторите новый пароль\" должны быть заполнены.");
				}
				
				if(!newPassword.equals(confirmNewPassword)) {
					messages.addErrorMessage("Ошибка. Пароли не совпадают.");
				}
			}
			
			if(messages.getErrorMessages().size() > 0) {
				return;
			}
			
			UserService service = new UserService();
			User user = null;
			
			try {
				user = service.getUserByID(am.getUserID());
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			user.setName(name);
			user.setSurname(surname);
			user.setPhone(phone);
			
			int result = 0;
			
			try {
				result = service.editUser(user);
				
				if(result > 0 && passwordChange) {
					
					String hashPassword = Encryption.hash(newPassword);
					result = service.changePassword(user.getId(), hashPassword);
				}
				
			} catch (ServiceException | SecurityException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				messages.addSuccessMessage("Данные успешно изменены");
			}
		}
	}
}
