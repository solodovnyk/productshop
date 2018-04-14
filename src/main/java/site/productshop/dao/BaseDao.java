package site.productshop.dao;

import javax.persistence.EntityManager;
import java.sql.Connection;

public abstract class BaseDao implements Dao {

	static int cnt;

	protected Connection getJDBCConnection() throws DaoException {
		return Persistence.getPersistence().getJDBCConnection();
	}

	protected EntityManager getEntityManager(String unitName) {
		return Persistence.getPersistence().getEntityManager(unitName);
	}
}