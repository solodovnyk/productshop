package site.productshop.dao;

import java.util.HashMap;

public class DaoManager {

	private static DaoManager dm = new DaoManager();
	private HashMap<String, Dao> daoPool = new HashMap<>();

	private DaoManager() {}

	public static DaoManager getInstance() {
		return DaoManager.dm;
	}

	private void saveDao(Dao dao) {
		daoPool.put(dao.getClass().getName(), dao);
	}

	private Dao loadDao(Class<? extends Dao> daoClass) {
		return daoPool.get(daoClass.getName());
	}

	public Dao getDao(Class<? extends Dao> daoClass) throws DaoException {
		Dao dao = loadDao(daoClass);
		try {
			return dao == null ? createDao(daoClass) : dao;
		} catch (DaoException e) {
			throw new DaoException(e.getMessage(), e);
		}
	}

	private Dao createDao(Class<? extends Dao> daoClass) throws DaoException {
		Dao dao;
		try {
			dao = daoClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DaoException(e.getMessage(), e);
		}
		saveDao(dao);
		return dao;
	}
}