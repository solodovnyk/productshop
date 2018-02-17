package com.productshop.services;

import java.util.ArrayList;
import com.productshop.core.CookieManager;
import com.productshop.dao.DaoManager;
import com.productshop.dao.DaoException;
import com.productshop.dao.OrderDao;
import com.productshop.dao.ProductpositionDao;
import com.productshop.models.Order;
import com.productshop.models.ProductPosition;

public class OrderService extends BaseService {

	public long createOrder(Order order, CookieManager cm) throws ServiceException {
		
		long orderID = 0;
		
		try {
			OrderDao orderDao = getDao(OrderDao.class);
			orderID = orderDao.addOrder(order);
			
			if(orderID > 0) {
				ProductpositionDao productPositionDao = getDao(ProductpositionDao.class);
				productPositionDao.addPositionsByOrderID(orderID, order.getProductPositions());
			}
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		if(orderID > 0) {
			cm.deleteAllCookies();
		}
		
		return orderID;
	}
	
	public ArrayList<Order> getAllOrders() throws ServiceException {
		
		ArrayList<Order> orders = new ArrayList<>();
		
		try {
			OrderDao orderDao = getDao(OrderDao.class);
			orders = orderDao.getAllOrders();
			
			if(orders.size() > 0) {
				for(Order order : orders) {
					prepareOrder(order);
				}
			}
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return orders;
	}
	
	public ArrayList<Order> getOrdersByUserID(int userID) throws ServiceException {
		
		ArrayList<Order> orders = new ArrayList<>();
		
		try {
			OrderDao orderDao = getDao(OrderDao.class);
			orders = orderDao.getOrdersByUserID(userID);
			
			if(orders.size() > 0) {
				for(Order order : orders) {
					prepareOrder(order);
				}
			}
			
		} catch (DaoException | ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return orders;
	}
	
	public int changeStatus(int orderID, int newStatus) throws ServiceException {
		
		int result = 0;
		
		try {
			OrderDao orderDao = getDao(OrderDao.class);
			result = (int) orderDao.changeStatus(orderID, newStatus);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}

	private Order prepareOrder(Order order) throws ServiceException {
		
		ArrayList<ProductPosition> positions = new ArrayList<>();
		
		ProductPositionService productPositionService = new ProductPositionService();
		
		try {
			positions = productPositionService.getProductPositionsByOrder(order);
			order.setProductPositions(positions);
		} catch (ServiceException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return order;
	}
}
