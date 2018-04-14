package site.productshop.services;

import java.util.ArrayList;

import site.productshop.dao.DaoException;
import site.productshop.dao.MessageDao;
import site.productshop.entities.Message;

public class MessageService extends BaseService {
	
	public long createNewMessage(Message message) throws ServiceException {
		
		long userID = 0;
		
		try {
			MessageDao dao = getDao(MessageDao.class);
			userID = dao.addMessage(message);
				
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return userID;
	}
	
	public ArrayList<Message> getAllMessages() throws ServiceException {
		
		ArrayList<Message> messages = new ArrayList<>();
		
		try {
			MessageDao messageDao = getDao(MessageDao.class);
			messages = messageDao.getAllMessages();
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return messages;
	}
	
	public Message getMessageByID(int messageID) throws ServiceException {
		
		Message message = null;
		
		try {
			MessageDao dao = getDao(MessageDao.class);
			message = dao.getMessageByID(messageID);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return message;
	}
	
	public int deleteMessage(int messageID) throws ServiceException {
		
		int result = 0;
		
		try {
			MessageDao dao = getDao(MessageDao.class);
			result = dao.deleteMessage(messageID);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
}
