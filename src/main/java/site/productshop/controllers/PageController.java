package site.productshop.controllers;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import site.productshop.core.Context;
import site.productshop.core.Messenger;
import site.productshop.entities.Item;
import site.productshop.entities.Message;
import site.productshop.services.CatalogService;
import site.productshop.services.MessageService;
import site.productshop.services.ServiceException;
import org.springframework.ui.Model;

@RequestMapping("/")
@Controller
public class PageController extends AbstractController {
    private CatalogService service = new CatalogService();

    @RequestMapping("/")
	public String showHomePage(Model uiModel) {
		ArrayList<Item> lastItems = null;
		ArrayList<Item> discountItems = null;
		
		try {
			lastItems = service.getLastItems(6);
			discountItems = service.getLastDiscountItems(6);
		} catch (ServiceException e) {
		    //если не найдено добавляется пустой список ???
			//throw new ControllerException(e.getMessage(), e);
		}

        uiModel.addAttribute("lastItems", lastItems);
        uiModel.addAttribute("discountItems", discountItems);

		return "first/index";
	}

	//TODO работа с формой
    @RequestMapping(value = "/", method = RequestMethod.POST)
	public void showMessagesPage() {
		Context context = getContext();

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
            //throw new ControllerException(e.getMessage(), e);
        }

        if(result > 0) {
            messenger.addSuccessMessage("Ваше сообщение было отправлено администратору.<br>Ожидайте ответ на указанный адрес электронной почты.");
        }

	}
	
	public void actionDelivery() throws ControllerException {}
}
