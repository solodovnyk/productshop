package site.productshop.controllers;

import org.springframework.http.ResponseEntity;
import site.productshop.entities.Entity;

import java.util.List;

public interface Controller<EntityType extends Entity> {
    ResponseEntity saveEntity(EntityType entity);
    ResponseEntity deleteEntity(Long entityId);
    ResponseEntity<EntityType> getEntity(Long entityId);
    ResponseEntity<List<EntityType>> getAllEntities();
}