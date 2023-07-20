package softuni.exam.util;

import jakarta.validation.ConstraintViolation;

public interface ValidationUtil {

    <E> boolean isValid(E entity);
}
