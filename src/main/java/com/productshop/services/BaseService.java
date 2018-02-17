package com.productshop.services;

import com.productshop.dao.Dao;
import com.productshop.dao.DaoException;
import com.productshop.dao.DaoManager;

public abstract class BaseService implements Service {

    protected <T extends Dao> T getDao(Class<T> daoClass) throws ServiceException {
        try {
            return (T)DaoManager.getInstance().getDao(daoClass);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}