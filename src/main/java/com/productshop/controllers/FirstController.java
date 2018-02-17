package com.productshop.controllers;

import java.util.ArrayList;
import com.productshop.core.*;
import com.productshop.models.*;
import com.productshop.services.*;

public class FirstController extends BaseController {

	public void actionIndex() throws ControllerException {
		CatalogService service = new CatalogService();
		ArrayList<Item> lastItems = null;
		ArrayList<Item> discountItems = null;
		
		try {
			lastItems = service.getLastItems(6);
			discountItems = service.getLastDiscountItems(6);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}

		getContext().setAttribute("lastItems", lastItems);
		getContext().setAttribute("discountItems", discountItems);
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
