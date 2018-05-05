package site.productshop.dao;

import java.sql.*;
import java.util.ArrayList;
import site.productshop.entities.User;

public class UserDao extends AbstractDao {
	
	public int getRoleByID(int userID) throws DaoException {
		
		String sql = "SELECT `role_id` FROM `users` WHERE `id` = ? AND `is_deleted` = 0 ";
		int roleID = 0;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, userID);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				roleID = resultSet.getInt("role_id");
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return roleID;
	}
	
	public long addUser(User user) throws DaoException {
		String name = user.getName();
		String surname = user.getSurname();
		String email = user.getEmail();
		String phone = user.getPhone();
		String password = user.getPassword();
		String sql = "INSERT INTO `users` (name, surname, email, phone, password) VALUES (?, ?, ?, ?, ?)";
		long resultID;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setString(3, email);
			statement.setString(4, phone);
			statement.setString(5, password);
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
	
	public boolean userExists(User user) throws DaoException {
		String userEmail = user.getEmail();
		String sql = "SELECT COUNT(*) FROM users WHERE email=?";
		int result = 0;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, userEmail);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result > 0;
	}
	
	public boolean userExists(String userEmail) throws DaoException {
		String sql = "SELECT COUNT(*) FROM users WHERE email=?";
		int result = 0;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, userEmail);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				result = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result > 0;
	}
	
	public String getHashPassword(String email) throws DaoException {
		String sql = "SELECT password FROM users WHERE email=?";
		String password = null;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				password = resultSet.getString(1);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return password;
	}
	
	public User getUser(String email) throws DaoException {
		String sql = 
				"SELECT `id`, `name`, `surname`, `email`, `phone`, `password`, `role_id`, `registration_date` "
				+ "FROM `users` WHERE email=? AND `is_deleted`=0";
		User user = null;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				user = new User(
					resultSet.getString("name"),
					resultSet.getString("surname"),
					resultSet.getString("email"),
					resultSet.getString("phone"),
					resultSet.getString("password")
				);
				user.setId(resultSet.getInt("id"));
				user.setRoleID(resultSet.getInt("role_id"));
				user.setRegistrationDate(resultSet.getDate("registration_date"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return user;
	}
	
	public User getUserByID(int userID) throws DaoException {
		String sql = 
				"SELECT `id`, `name`, `surname`, `email`, `phone`, `password`, `role_id`, `registration_date` "
				+ "FROM `users` WHERE id=? AND `is_deleted`=0";
		User user = null;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, userID);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				user = new User(
					resultSet.getString("name"),
					resultSet.getString("surname"),
					resultSet.getString("email"),
					resultSet.getString("phone"),
					resultSet.getString("password")
				);
				user.setId(resultSet.getInt("id"));
				user.setRoleID(resultSet.getInt("role_id"));
				user.setRegistrationDate(resultSet.getDate("registration_date"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return user;
	}
	
	public ArrayList<User> getAllUsers() throws DaoException {
		String sql = "SELECT id,name,surname,email,phone,password,role_id,registration_date FROM users "
				+ "WHERE is_deleted = 0";
		ArrayList<User> users = new ArrayList<>();
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				User user = new User(
					resultSet.getString("name"),
					resultSet.getString("surname"),
					resultSet.getString("email"),
					resultSet.getString("phone"),
					resultSet.getString("password")
				);
				user.setId(resultSet.getInt("id"));
				user.setRoleID(resultSet.getInt("role_id"));
				user.setRegistrationDate(resultSet.getDate("registration_date"));
				users.add(user);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return users;
	}
	
	public int editUser(User user) throws DaoException {
		int userID = user.getId();
		String name = user.getName();
		String surname = user.getSurname();
		String phone = user.getPhone();
		String sql = "UPDATE `users` SET name=?, surname=?, phone=? WHERE id=?";
		int result;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setString(2, surname);
			statement.setString(3, phone);
			statement.setInt(4, userID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public int deleteUser(int userID) throws DaoException {
		String sql = "UPDATE `users` SET is_deleted=1 WHERE id=?";
		int result;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, userID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
	
	public int editUserPassword(int userID, String password) throws DaoException {
		String sql = "UPDATE `users` SET password=? WHERE id=?";
		int result;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, password);
			statement.setInt(2, userID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
}