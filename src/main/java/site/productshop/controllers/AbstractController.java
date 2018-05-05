package site.productshop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import site.productshop.entities.Entity;
import site.productshop.services.Service;
import site.productshop.util.exceptions.EntityNotFoundException;
import site.productshop.validators.Validator;
import java.util.List;

public abstract class AbstractController<EntityType extends Entity> implements Controller<EntityType> {
    private Class<EntityType> entityType;
    private Service<EntityType> service;
    private Validator<EntityType> validator;

    public AbstractController(Class<EntityType> entityType) {
        this.entityType = entityType;
    }

    @Override
    @ResponseBody
    public ResponseEntity saveEntity(@RequestBody EntityType entity) {
        try {
            if(!validateEntity(entity))
                return generateResponse(HttpStatus.BAD_REQUEST);
            service.save(entity);
        } catch (Exception e) {
            return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return generateResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity deleteEntity(Long id) {
        try {
            if(!validateEntityId(id))
                return generateResponse(HttpStatus.BAD_REQUEST);
            EntityType entity = service.findById(id);
            service.delete(entity);
        } catch (EntityNotFoundException e) {
            return generateResponse(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return generateResponse(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<EntityType> getEntity(Long id) {
        EntityType entity;
        try {
            if(!validateEntityId(id))
                return generateResponse(HttpStatus.BAD_REQUEST);
            entity = service.findById(id);
        } catch (EntityNotFoundException e) {
            return generateResponse(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return generateResponseWithEntity(HttpStatus.OK, entity);
    }

    @Override
    public ResponseEntity<List<EntityType>> getAllEntities() {
        List<EntityType> entities;
        try {
            entities = service.findAll();
        } catch (Exception e) {
            return generateResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return generateResponseWithEntity(HttpStatus.OK, entities);
    }

    protected <ReturnType> ResponseEntity<ReturnType> generateResponseWithEntity(HttpStatus status, ReturnType result) {
        return new ResponseEntity<>(result, status);
    }

    protected ResponseEntity generateResponse(HttpStatus status) {
        return new ResponseEntity(status);
    }

    protected boolean validateEntity(EntityType entity) {
        if(!validator.supports(entityType))
            return false;
        BindingResult validationResult =  validator.validate(entity);
        if(validationResult.hasErrors())
            return false;
        return true;
    }

    protected boolean validateEntityId(Long id) {
        return id != null && id > 0;
    }

    public void getEntityType(Class<EntityType> entityType) {
        this.entityType = entityType;
    }

    public Service<EntityType> getService() {
        return service;
    }

    protected void setService(Service<EntityType> service) {
        this.service = service;
    }

    protected void setValidator(Validator<EntityType> validator) {
        this.validator = validator;
    }
}