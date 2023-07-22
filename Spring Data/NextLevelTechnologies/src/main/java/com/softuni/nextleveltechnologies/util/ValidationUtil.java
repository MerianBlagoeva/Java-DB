package com.softuni.nextleveltechnologies.util;

import jakarta.validation.ConstraintViolation;

public interface ValidationUtil {

    <E> boolean isValid(E entity);
}
