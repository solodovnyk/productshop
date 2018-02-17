package com.productshop.services;

import java.util.ArrayList;
import com.productshop.dao.*;
import com.productshop.models.User;
import com.productshop.security.AuthenticationManager;

public class UserService extends BaseService {

	public int getRoleByUserID(int userID) throws ServiceException {
		
		int userRole = 0;
		
		try {
			UserDao dao = getDao(UserDao.class);
			userRole = dao.getRoleByID(userID);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return userRole;
	}
	
	public User getUserByID(int userID) throws ServiceException {
		
		User user = null;
		
		try {
			UserDao dao = getDao(UserDao.class);
			user = dao.getUserByID(userID);
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return user;
	}
	
	public long createNewUser(User user, AuthenticationManager authManager) throws ServiceException {
		
		long userID = 0;
		
		try {
			UserDao dao = getDao(UserDao.class);
			
			if(!dao.userExists(user)) {
				userID = dao.addUser(user);
				user = dao.getUser(user.getEmail());
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		if(userID > 0) {
			authManager.createNewAuthentication(user);
		}
		
		return userID;
	}
	
	public boolean authUser(String email, String password, AuthenticationManager authManager) throws ServiceException {
		
		boolean result = false;
		User user = null;
		
		try {
			UserDao dao = getDao(UserDao.class);
			
			if(dao.userExists(email)) {
				String userHashPassword = dao.getHashPassword(email);
				
				if(password.equals(userHashPassword)) {
					user = dao.getUser(email);
					
					if(user != null) {
						result = true;
					}
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		if(result) {
			authManager.createNewAuthentication(user);
		}
		
		return result;
	}
	
	public ArrayList<User> getAllUsers() throws ServiceException {
		
		ArrayList<User> users = new ArrayList<>();
		
		try {
			UserDao userDao = getDao(UserDao.class);
			users = userDao.getAllUsers();
			
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return users;
	}
	
	public int editUser(User user) throws ServiceException {
		
		int result = 0;
		
		try {
			UserDao dao = getDao(UserDao.class);
			result = dao.editUser(user);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int deleteUser(int userID) throws ServiceException {
		
		int result = 0;
		
		try {
			UserDao dao = getDao(UserDao.class);
			result = dao.deleteUser(userID);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
	
	public int changePassword(int userID, String password) throws ServiceException {
		
		int result = 0;
		
		try {
			UserDao dao = getDao(UserDao.class);
			result = dao.editUserPassword(userID, password);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		
		return result;
	}
}
