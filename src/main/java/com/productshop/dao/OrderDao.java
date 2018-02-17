package com.productshop.dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import com.productshop.models.Order;
import com.productshop.models.User;
import com.productshop.services.ServiceException;
import com.productshop.services.UserService;

public class OrderDao extends BaseDao {

	public long addOrder(Order order) throws DaoException {
		int userID = order.getUser().getId();
		String sql = "INSERT INTO `orders`(user_id) VALUES (?)";
		long resultID;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, userID);
			resultID = statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                resultID = generatedKeys.getLong(1);
	            }
	        }
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return resultID;
	}

	public ArrayList<Order> getAllOrders() throws DaoException {
		String sql = "SELECT id,user_id,order_status_id,order_date FROM orders";
		ArrayList<Order> orders = new ArrayList<>();
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				UserService userService = new UserService();
				User user;
				try {
					user = userService.getUserByID(resultSet.getInt("user_id"));
				} catch (ServiceException e) {
					throw new DaoException(e.getMessage(), e);
				}
				Order order = new Order(user);
				order.setId(resultSet.getInt("id"));
				order.setOrderStatusID(resultSet.getInt("order_status_id"));
				order.setOrderDate(resultSet.getDate("order_date"));
				orders.add(order);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return orders;
	}
	
	public ArrayList<Order> getOrdersByUserID(int userID) throws DaoException {
		String sql = "SELECT id,user_id,order_status_id,order_date FROM orders WHERE user_id=?";
		ArrayList<Order> orders = new ArrayList<>();
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, userID);
			ResultSet resultSet = statement.executeQuery();
			statement.setInt(1, userID);
			while(resultSet.next()) {
				UserService userService = new UserService();
				User user;
				try {
					user = userService.getUserByID(resultSet.getInt("user_id"));
				} catch (ServiceException e) {
					throw new DaoException(e.getMessage(), e);
				}
				Order order = new Order(user);
				order.setId(resultSet.getInt("id"));
				order.setOrderStatusID(resultSet.getInt("order_status_id"));
				order.setOrderDate(resultSet.getDate("order_date"));
				orders.add(order);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return orders;
	}
	
	public long changeStatus(int orderID, int newStatus) throws DaoException {
		int result;
		String sql = "UPDATE orders SET order_status_id=? WHERE id=?";
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, newStatus);
			statement.setInt(2, orderID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
}