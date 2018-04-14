package site.productshop.services;

import java.util.ArrayList;
import site.productshop.dao.DaoException;
import site.productshop.dao.ProductpositionDao;
import site.productshop.entities.Order;
import site.productshop.entities.ProductPosition;

public class ProductPositionService extends BaseService {

	public ArrayList<ProductPosition> getProductPositionsByOrder(Order order) throws ServiceException {
		
		ArrayList<ProductPosition> positions = new ArrayList<>();
		
		try {
			ProductpositionDao positionDao = getDao(ProductpositionDao.class);
			positions = positionDao.getProductPositionsByOrder(order);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return positions;
	}
}
