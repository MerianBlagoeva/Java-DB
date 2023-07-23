package softuni.exam.instagraphlite.util.impl;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import softuni.exam.instagraphlite.util.ValidationUtil;

import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl() {
        validator = Validation.buildDefaultValidatorFactory()
                .getValidator();
    }

    @Override
    public <T> boolean isValid(T entity) {
        return validator.validate(entity).isEmpty();
    }

//    @Override
//    public <T> Set<ConstraintViolation<T>> violations(T entity) {
//        return validator.validate(entity);
//    }

}
