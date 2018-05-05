package site.productshop.services;

import site.productshop.entities.Entity;
import site.productshop.util.exceptions.EntityNotFoundException;

import java.util.List;

public interface Service<EntityType extends Entity> {
    EntityType findById(Long id) throws EntityNotFoundException;
    List<EntityType> findAll() throws Exception;
    void save(EntityType entity);
    void delete(EntityType entity);
}
