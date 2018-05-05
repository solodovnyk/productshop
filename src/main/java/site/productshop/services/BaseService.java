package site.productshop.services;

import site.productshop.dao.Dao;
import site.productshop.dao.DaoException;
import site.productshop.dao.DaoManager;

public abstract class BaseService implements Service {

    protected <T extends Dao> T getDao(Class<T> daoClass) throws ServiceException {
        try {
            return (T) DaoManager.getInstance().getDao(daoClass);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}