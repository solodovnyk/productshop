package site.productshop.services;

import site.productshop.dao.Dao;
import site.productshop.entities.Entity;
import site.productshop.util.exceptions.EntityNotFoundException;

import java.util.List;

public abstract class AbstractService<EntityType extends Entity> implements Service<EntityType> {
    private Class<EntityType> entityType;
    private Dao<EntityType> dao;

    public AbstractService(Class<EntityType> entityType) {
        this.entityType = entityType;
    }

    @Override
    public EntityType findById(Long id) throws EntityNotFoundException {
        EntityType entity = dao.findById(id);
        if(entity == null)
            throw new EntityNotFoundException("Entity has not found");
        return entity;
    }

    @Override
    public List<EntityType> findAll() throws Exception {
        List<EntityType> entities = dao.findAll();
        if(entities == null)
            throw new Exception("Result should not be null");
        return entities;
    }

    @Override
    public void save(EntityType entity) {
        dao.save(entity);
    }

    @Override
    public void delete(EntityType entity) {
        dao.delete(entity);
    }

    public Class<EntityType> getEntityType() {
        return entityType;
    }

    public Dao<EntityType> getDao() {
        return dao;
    }

    public void setDao(Dao<EntityType> dao) {
        this.dao = dao;
    }
}