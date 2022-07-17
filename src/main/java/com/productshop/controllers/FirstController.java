package com.productshop.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.productshop.core.*;
import com.productshop.models.*;
import com.productshop.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/first")
public class FirstController extends BaseController {

	@GetMapping("/")
	public String actionIndex(Model model) throws ControllerException {
		CatalogService service = new CatalogService();
		ArrayList<Item> lastItems;
		ArrayList<Item> discountItems;
		try {
			lastItems = service.getLastItems(6);
			discountItems = service.getLastDiscountItems(6);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		model.addAttribute("lastItems", lastItems);
		model.addAttribute("discountItems", discountItems);
		model.addAttribute("page", "first/index");
		return MAIN_LAYOUT_PATH;
	}
	
	public void actionMessages() throws ControllerException {
		Context context = getContext();

		if(context.isMethodPOST()) {
			String name = context.getRequestParameter("name");
			String email = context.getRequestParameter("email");
			String question = context.getRequestParameter("question");
			
			Message message = new Message(name, email, question);
			Messenger messenger = context.getMessenger();
			
			if(name.isEmpty() || email.isEmpty() || question.isEmpty()) {
				messenger.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			}
			
			if(messenger.getErrorMessages().size() > 0) {
				context.setAttribute("message", message);
				return;
			}
			
			MessageService service = new MessageService();
			long result = 0;
			
			try {
				result = service.createNewMessage(message);
			} catch (ServiceException e) {
				throw new ControllerException(e.getMessage(), e);
			}
			
			if(result > 0) {
				messenger.addSuccessMessage("Ваше сообщение было отправлено администратору.<br>Ожидайте ответ на указанный адрес электронной почты.");
			}
		}
	}
	
	public void actionDelivery() throws ControllerException {}

}
