package site.productshop.controllers;

import java.util.ArrayList;
import site.productshop.entities.Item;
import site.productshop.services.CatalogService;
import site.productshop.services.ServiceException;

public class SearchController extends AbstractController {
	
	public void actionIndex() throws ControllerException {
		if(getContext().isMethodPOST()) {
			String keyword = getContext().getRequestParameter("keyword");
			
			if(!keyword.isEmpty()) {
				CatalogService service = new CatalogService();
				ArrayList<Item> items = null;
				int itemsQuantityInSearchResults = 0;
				
				try {
					items = service.findItemsByKeyword(keyword, 1, 1);
					itemsQuantityInSearchResults = service.getItemsQuantityInSearchResults(keyword);
				} catch (ServiceException e) {
					throw new ControllerException(e.getMessage(), e);
				}
				
				getContext().setAttribute("keyword", keyword);
				getContext().setAttribute("items", items);
				getContext().setAttribute("itemsQuantityInSearchResults", itemsQuantityInSearchResults);
				getContext().setAttribute("pageNumber", 1);
			}
		}
	}
	
	public void actionFind(String keyword, int sortMode, int pageNum) throws ControllerException {
		CatalogService service = new CatalogService();
		ArrayList<Item> items = null;
		int itemsQuantityInSearchResults = 0;
		
		try {
			items = service.findItemsByKeyword(keyword, sortMode, pageNum);
			itemsQuantityInSearchResults = service.getItemsQuantityInSearchResults(keyword);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}

		getContext().setAttribute("keyword", keyword);
		getContext().setAttribute("items", items);
		getContext().setAttribute("itemsQuantityInSearchResults", itemsQuantityInSearchResults);
		getContext().setAttribute("pageNumber", pageNum);
	}

}
