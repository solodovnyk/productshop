package site.productshop.dao;

import java.sql.*;
import java.util.ArrayList;
import site.productshop.entities.Message;

public class MessageDao extends AbstractDao {
	
	public long addMessage(Message message) throws DaoException {
		String name = message.getSenderName();
		String email = message.getSenderEmail();
		String text = message.getText();
		String sql = "INSERT INTO `messages` (sender_name, sender_email, message_text) VALUES (?, ?, ?)";
		long resultID;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setString(2, email);
			statement.setString(3, text);
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
	
	public ArrayList<Message> getAllMessages() throws DaoException {
		String sql = "SELECT id,sender_name,sender_email,message_text,message_text,message_date FROM messages "
				+ "WHERE is_deleted = 0";
		ArrayList<Message> messages = new ArrayList<>();
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				Message message = new Message(
					resultSet.getString("sender_name"),
					resultSet.getString("sender_email"),
					resultSet.getString("message_text")
				);
				message.setId(resultSet.getInt("id"));
				message.setMessageDate(resultSet.getDate("message_date"));
				messages.add(message);
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return messages;
	}
	
	public Message getMessageByID(int messageID) throws DaoException {
		String sql = "SELECT id,sender_name,sender_email,message_text,message_date FROM messages" +
				" WHERE id=? AND `is_deleted`=0";
		Message message = null;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, messageID);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				message = new Message(
					resultSet.getString("sender_name"),
					resultSet.getString("sender_email"),
					resultSet.getString("message_text")
				);
				message.setId(resultSet.getInt("id"));
				message.setMessageDate(resultSet.getDate("message_date"));
			}
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return message;
	}
	
	public int deleteMessage(int messageID) throws DaoException {
		String sql = "UPDATE `messages` SET is_deleted=1 WHERE id=?";
		int result;
		try (Connection connection = getJDBCConnection();
			 PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, messageID);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException(e.getMessage(), e);
		}
		return result;
	}
}
