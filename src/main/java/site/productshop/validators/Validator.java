package site.productshop.validators;

import org.springframework.validation.BindingResult;
import site.productshop.entities.Entity;

public interface Validator<EntityType extends Entity> extends org.springframework.validation.Validator {
    BindingResult validate(EntityType entity);
}
