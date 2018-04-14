package site.productshop.dao;

import site.productshop.entities.Entity;
import site.productshop.util.exceptions.EntityNotFoundException;

import java.util.List;

public interface Dao<EntityType extends Entity> {
    EntityType findById(Long id);
    List<EntityType> findAll();
    void save(EntityType entity);
    void delete(EntityType entity);
}
