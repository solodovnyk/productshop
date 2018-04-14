package site.productshop.controllers;

import java.util.ArrayList;

import site.productshop.entities.Category;
import site.productshop.entities.Item;
import site.productshop.services.CatalogService;
import site.productshop.services.ServiceException;

public class CatalogController extends AbstractController {
	
	public void actionCategory(String categorySlug) throws ControllerException {
		CatalogService service = new CatalogService();
		Category category = null;
		
		try {
			category = service.getCategoryBySlug(categorySlug);
			
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("category", category);
	}
	
	public void actionSubcategory(String subcategorySlug, int sortMode, int pageNum) throws ControllerException {
		CatalogService service = new CatalogService();
		Category subcategory = null;
		ArrayList<Item> items = null;
		int itemsQuantityInCategory = 0;
		
		try {
			subcategory = service.getCategoryBySlug(subcategorySlug);
			items = service.getItemsBySubcategory(subcategory, sortMode, pageNum);
			itemsQuantityInCategory = service.getItemsQuantityBySubcategory(subcategory);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}

		getContext().setAttribute("subcategory", subcategory);
		getContext().setAttribute("items", items);
		getContext().setAttribute("itemsQuantityInCategory", itemsQuantityInCategory);
		getContext().setAttribute("pageNumber", pageNum);
	}
	
	public void actionItem(String itemCode) throws ControllerException {
		CatalogService service = new CatalogService();
		Item item = null;
		
		try {
			item = service.getItemByCode(itemCode);
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage(), e);
		}
		
		getContext().setAttribute("item", item);
	}
}
