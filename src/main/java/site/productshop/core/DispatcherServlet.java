package site.productshop.core;

import java.util.ArrayList;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import site.productshop.controllers.ControllerManager;
import site.productshop.entities.Category;
import site.productshop.router.Router;
import site.productshop.services.CatalogService;
import site.productshop.view_loader.ViewLoader;

@MultipartConfig(maxFileSize = 16177215)
public class DispatcherServlet extends HttpServlet {

	private final boolean isDebug = true;
	
	private String shopName;
	private String adminEmail;
	private String adminPhone;
	private String itemQuantity;
	private String shopCurrency;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		final String configPath = getServletContext().getRealPath(getInitParameter("config"));

		try {
			Context context = new Context(request, response, configPath);

			/*
			this.shopName = Configurations.getComponentValue("settings", "shopName");
			this.adminEmail = Configurations.getComponentValue("settings", "adminEmail");
			this.adminPhone = Configurations.getComponentValue("settings", "adminPhone");
			this.itemQuantity = Configurations.getComponentValue("settings", "itemQuantity");
			this.shopCurrency = Configurations.getComponentValue("settings", "shopCurrency");

			RedirectManager rm = new RedirectManager(response);
			CookieManager cm = new CookieManager(request, response);

			request.setAttribute("redirectManager", rm);
			request.setAttribute("cookieManager", cm);
			request.setAttribute("messages", new Messenger());
			request.setAttribute("authManager", new AuthenticationManager(request));
			*/


			CatalogService catalogService = new CatalogService();

			ArrayList<Category> categories = catalogService.getAllCategories();

			request.setAttribute("allCategories", categories);

			/*
			request.setAttribute("shopName", this.shopName);
			request.setAttribute("adminEmail", this.adminEmail);
			request.setAttribute("adminPhone", this.adminPhone);
			request.setAttribute("itemQuantity", this.itemQuantity);
			request.setAttribute("shopCurrency", this.shopCurrency);
			request.setAttribute("year", new GregorianCalendar().get(Calendar.YEAR));
			*/


			Router.determineInternalRoute(context);
			ControllerManager.get().run(context);

			if(context.getRedirectManager().isRedirectAllowed()) {
				return;
			}

			ViewLoader.get().run(context);

		} catch (Exception e) {
			processException(e);
		}
	}

    private void processException(Exception e) {
    	Exception targetException = getTargetException(e);
		
    	if(isDebug == true) {
    		targetException.printStackTrace();
    	}
    }
    
    private Exception getTargetException(Exception exception) {
    	Exception cause = (Exception) exception.getCause();
    	
    	if(cause == null) {
    		return exception;
    	}
    	
    	return getTargetException(cause);
    }
}
