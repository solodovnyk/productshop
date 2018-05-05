package site.productshop.dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import site.productshop.entities.Item;
import site.productshop.entities.Order;
import site.productshop.entities.ProductPosition;
import site.productshop.services.CatalogService;
import site.productshop.services.ServiceException;

public class ProductpositionDao extends AbstractDao {
	public void addPositionsByOrderID(long orderID, ArrayList<ProductPosition> productPositions) throws DaoException {
		String sql = "INSERT INTO `product_position`(order_id,item_id,quantity,price) VALUES (?,?,?,?)";
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			for(ProductPosition position : productPositions) {
				statement.setInt(1, (int)orderID);
				statement.setInt(2, position.getItem().getId());
				statement.setShort(3, (short)position.getQuantity());
				statement.setInt(4, (int)(position.getPrice().doubleValue() * 100));
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
	}
	
	public ArrayList<ProductPosition> getProductPositionsByOrder(Order order) throws DaoException {
		int orderID = order.getId();
		String sql = "SELECT id,order_id,item_id,quantity,price FROM product_position WHERE order_id = ?";
		ArrayList<ProductPosition> positions = new ArrayList<>();
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, orderID);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				CatalogService catalogService = new CatalogService();
				Item item;
				int itemID = resultSet.getInt("item_id");
				try {
					item = catalogService.getItemByCode(String.valueOf(itemID));
				} catch (ServiceException e) {
					throw new DaoException(e.getMessage(), e);
				}
				int quantity = resultSet.getInt("quantity");
				BigDecimal price = new BigDecimal(((double)resultSet.getInt("price")) / 100);
				ProductPosition position = new ProductPosition(item, quantity, price);
				position.setId(resultSet.getInt("id"));
				position.setOrderID(resultSet.getInt("order_id"));
				positions.add(position);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return positions;
	}
}
