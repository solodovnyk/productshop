package com.productshop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.productshop.core.Messenger;
import com.productshop.core.RedirectManager;
import com.productshop.models.Order;
import com.productshop.models.User;
import com.productshop.security.AuthenticationManager;
import com.productshop.security.Encryption;
import com.productshop.security.SecurityException;
import com.productshop.services.CatalogService;
import com.productshop.services.OrderService;
import com.productshop.services.ServiceException;
import com.productshop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
public class UserController extends BaseController {

	private final UserService userService = new UserService();
	private final OrderService orderService = new OrderService();

	public void actionIndex() throws ControllerException {

		//todo replace with prepareModelForAccountPage method

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
	public String renderLoginPage(Model model, AuthenticationManager authManager) throws ServiceException {
		model.addAttribute("authManager", authManager);
		if(authManager.isAuthenticated()) {
			User user = userService.getUserByID(authManager.getUserID());
			prepareModelForAccountPage(model, user, orderService.getOrdersByUserID(user.getId()));
			return MAIN_LAYOUT_PATH;
		}
		model.addAttribute("page", "user/login");
		return MAIN_LAYOUT_PATH;
	}

	@PostMapping("/login")
	public String processLoginRequest(Model model,
							AuthenticationManager authManager,
							Messenger messenger,
							@RequestParam(required = false) String email,
							@RequestParam(required = false) String password
							) throws ControllerException, ServiceException {
		model.addAttribute("authManager", authManager);
		model.addAttribute("messages", messenger);

		if(email.isBlank() || password.isBlank()) {
			messenger.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			model.addAttribute("email", email);
			model.addAttribute("page", "user/login");
			return MAIN_LAYOUT_PATH;
		}

		boolean result;

		try {
			String hashPassword = Encryption.hash(password);
			result = userService.authUser(email, hashPassword, authManager);
		} catch (ServiceException | SecurityException e) {
			throw new ControllerException(e.getMessage(), e);
		}

		if(!result) {
			messenger.addErrorMessage("Ошибка. Неверный логин или пароль.");
			model.addAttribute("email", email);
			model.addAttribute("page", "user/login");
			return MAIN_LAYOUT_PATH;
		} else {
			User user = userService.getUserByID(authManager.getUserID());
			prepareModelForAccountPage(model, user, orderService.getOrdersByUserID(user.getId()));
		}
		return MAIN_LAYOUT_PATH;
	}

	@GetMapping("/registration")
	public String renderRegistrationPage(Model model, AuthenticationManager authManager) throws ServiceException {
		model.addAttribute("authManager", authManager);
		if(authManager.isAuthenticated()) {
			User user = userService.getUserByID(authManager.getUserID());
			prepareModelForAccountPage(model, user, orderService.getOrdersByUserID(user.getId()));
			return MAIN_LAYOUT_PATH;
		}
		model.addAttribute("page", "user/registration");
		return MAIN_LAYOUT_PATH;
	}

	@PostMapping("/registration")
	public String processRegistrationRequest(Model model,
								   AuthenticationManager authManager,
								   Messenger messenger,
								   @RequestParam(required = false) String name,
								   @RequestParam(required = false) String surname,
								   @RequestParam(required = false) String email,
								   @RequestParam(required = false) String phone,
								   @RequestParam(required = false) String password,
								   @RequestParam(required = false, value = "confirm-password") String confirmedPassword
								   ) throws ControllerException, ServiceException {
		model.addAttribute("authManager", authManager);
		model.addAttribute("messages", messenger);
		if(authManager.isAuthenticated()) {
			User user = userService.getUserByID(authManager.getUserID());
			prepareModelForAccountPage(model, user, orderService.getOrdersByUserID(user.getId()));
			return MAIN_LAYOUT_PATH;
		}
		User user = new User(name, surname, email, phone, password);
		model.addAttribute("user", user);

		if(name.isBlank() || surname.isBlank() || email.isBlank() || phone.isBlank() ||
				password.isBlank() || confirmedPassword.isBlank()) {
			messenger.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			model.addAttribute("page", "user/registration");
			return MAIN_LAYOUT_PATH;
		}

		if(!password.equals(confirmedPassword)) {
			messenger.addErrorMessage("Ошибка. Пароли не совпадают.");
			model.addAttribute("page", "user/registration");
			return MAIN_LAYOUT_PATH;
		}

		long result;

		try {
			String hashPassword = Encryption.hash(password);
			user.setPassword(hashPassword);
			result = userService.createNewUser(user, authManager);
		} catch (ServiceException | SecurityException e) {
			throw new ControllerException(e.getMessage(), e);
		}

		if(result > 0) {
			User persistedUser = userService.getUserByID(result);
			prepareModelForAccountPage(model, persistedUser, orderService.getOrdersByUserID(persistedUser.getId()));
			return MAIN_LAYOUT_PATH;
		} else {
			messenger.addErrorMessage("Ошибка. Регистрация не была завершена.");
			model.addAttribute("page", "user/registration");
			return MAIN_LAYOUT_PATH;
		}
	}

	@GetMapping("/out")
	public String logoutUser(Model model, AuthenticationManager authManager) throws ControllerException {
		model.addAttribute("authManager", authManager);
		if(authManager.isAuthenticated()) {
			authManager.destroyAuthentication();
		}
		try {
			model.addAttribute("lastItems", new CatalogService().getLastItems(6));
			model.addAttribute("discountItems", new CatalogService().getLastDiscountItems(6));
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("page", "index");
		return MAIN_LAYOUT_PATH;
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

	private void prepareModelForAccountPage(Model model, User user, List<Order> orders) {
		model.addAttribute("user", user);
		model.addAttribute("orders", orders);
		model.addAttribute("page", "user/account");
	}
}
