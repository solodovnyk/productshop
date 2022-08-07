package com.productshop.dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

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

    private void createConnectionPool() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/product_shop");
        dataSource.setUsername("root");
        dataSource.setPassword("55555");
        connectionPool = dataSource;
    }

    private void createEntityManagerFactory(String unitName) {
        EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory(unitName);
        entityManagerFactoryPool.put(unitName, emf);
    }
}
