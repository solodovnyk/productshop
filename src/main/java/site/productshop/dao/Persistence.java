package site.productshop.dao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class Persistence {

    private static Persistence persistence = new Persistence();
    private DataSource connectionPool;
    private HashMap<String, EntityManagerFactory> entityManagerFactoryPool = new HashMap<>();

    private Persistence() {
    }

    public static Persistence getPersistence() {
        return Persistence.persistence;
    }

    public Connection getJDBCConnection() throws DaoException {
        if(connectionPool == null)
            createConnectionPool();
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    public EntityManager getEntityManager(String unitName) {
        if(entityManagerFactoryPool.get(unitName) == null)
            createEntityManagerFactory(unitName);
        return entityManagerFactoryPool.get(unitName).createEntityManager();
    }

    private void createConnectionPool() throws DaoException {
        try {
            connectionPool = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/product_shop");
        } catch (NamingException e) {
            throw new DaoException(e.getMessage(), e);
        }
    }

    private void createEntityManagerFactory(String unitName) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory(unitName);
        entityManagerFactoryPool.put(unitName, emf);
    }
}
