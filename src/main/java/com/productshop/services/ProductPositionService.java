package com.productshop.services;

import java.util.ArrayList;
import com.productshop.dao.DaoException;
import com.productshop.dao.ProductpositionDao;
import com.productshop.models.Order;
import com.productshop.models.ProductPosition;

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
