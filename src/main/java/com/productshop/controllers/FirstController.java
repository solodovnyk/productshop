package com.productshop.controllers;

import java.util.ArrayList;

import com.productshop.core.*;
import com.productshop.models.*;
import com.productshop.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class FirstController extends BaseController {

	@GetMapping("/")
	public String renderIndexPage(Model model) throws ControllerException {
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
		model.addAttribute("page", "index");
		return MAIN_LAYOUT_PATH;
	}

	@GetMapping("/messages")
	public String renderMessagesPage(Model model) {
		model.addAttribute("page", "messages");
		return MAIN_LAYOUT_PATH;
	}

	@PostMapping("/messages")
	public String processMessage(Model model,
								 @Autowired Messenger messenger,
								 @RequestParam String name,
								 @RequestParam String email,
								 @RequestParam String question) {
		model.addAttribute("messages", messenger);
		model.addAttribute("page", "messages");
		Message message = new Message(name, email, question);
		if(name.isEmpty() || email.isEmpty() || question.isEmpty()) {
			messenger.addErrorMessage("Ошибка. Все поля обязательны для заполнения.");
			return MAIN_LAYOUT_PATH;
		}

		MessageService service = new MessageService();
		long result = 0;

		try {
			result = service.createNewMessage(message);
		} catch (ServiceException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}

		if(result > 0) {
			messenger.addSuccessMessage("Ваше сообщение было отправлено администратору.<br>Ожидайте ответ на указанный адрес электронной почты.");
		}

		return MAIN_LAYOUT_PATH;
	}

	@GetMapping("/delivery")
	public String renderDeliveryPage(Model model) {
		model.addAttribute("page", "delivery");
		return MAIN_LAYOUT_PATH;
	}
}
