package site.productshop.services;

import java.util.ArrayList;
import site.productshop.core.CookieManager;
import site.productshop.dao.DaoException;
import site.productshop.dao.OrderDao;
import site.productshop.dao.ProductpositionDao;
import site.productshop.entities.Order;
import site.productshop.entities.ProductPosition;

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
