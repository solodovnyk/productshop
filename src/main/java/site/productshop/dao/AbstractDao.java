package site.productshop.dao;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import site.productshop.entities.Entity;
import javax.sql.DataSource;

public abstract class AbstractDao<EntityType extends Entity> implements Dao<EntityType>, InitializingBean {
	private Class<EntityType> entityType;
	private JdbcTemplate operations;
	private DataSource dataSource;

	public AbstractDao(Class<EntityType> entityType) {
		this.entityType = entityType;
	}

	public Class<EntityType> getEntityType() {
		return entityType;
	}

	public void setOperations(JdbcTemplate operations) {
		this.operations = operations;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(dataSource == null)
			throw new BeanCreationException("Data source has not been set on Dao object");
		if(operations == null)
			throw new BeanCreationException("JDBC template has not been set on Dao object");
		operations.setDataSource(dataSource);
	}
}