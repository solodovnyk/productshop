package site.productshop.validators;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import site.productshop.entities.Entity;

public abstract class AbstractValidator<EntityType extends Entity> implements Validator<EntityType> {
    private Class<EntityType> entityType;

    AbstractValidator(Class<EntityType> entityType) {
        this.entityType = entityType;
    }

    @Override
    public BindingResult validate(EntityType entity) {
        BeanPropertyBindingResult validationResult = new BeanPropertyBindingResult(entity, entityType.getName());
        ValidationUtils.invokeValidator(this, entity, validationResult);
        return validationResult;
    }

    @Override
    public boolean supports(Class<?> type) {
        return entityType.equals(type);
    }

    @Override
    public abstract void validate(Object object, Errors errors);
}
